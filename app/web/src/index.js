import React, { Component } from 'react';
import { render } from 'react-dom';
import { Button, Container, Row, Col, Alert } from 'reactstrap';
import { AvGroup, AvForm, AvInput, AvFeedback } from 'availity-reactstrap-validation';
import './style.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from 'axios'


function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}

class Board extends Component {
    renderSquare(i) {
        return (
            <Square
                value={this.props.squares[i]}
                onClick={() => this.props.onClick(i)}
            />
        );
    }

    render() {
        return (
            <div>
                <div className="board-row">
                    {this.renderSquare(0)}
                    {this.renderSquare(1)}
                    {this.renderSquare(2)}
                </div>
                <div className="board-row">
                    {this.renderSquare(3)}
                    {this.renderSquare(4)}
                    {this.renderSquare(5)}
                </div>
                <div className="board-row">
                    {this.renderSquare(6)}
                    {this.renderSquare(7)}
                    {this.renderSquare(8)}
                </div>
            </div>
        );
    }
}

class Game extends React.Component {
    constructor() {
        super();
        this.state = {
            playerName: '',
            playerId: -1,
            gameId: -1,
            move: -1,
            squares: Array(9).fill(null),
            status: -1,
            error: ''
        };
    }

    onChange = (e) => {
        this.setState({ [e.target.name]: e.target.value });
    }

    onSubmit = () => {
        const { playerName } = this.state;

        axios.get('/start', { params: { playerName } })
            .then((result) => {
                this.setState({ playerId: result.data.playerId, gameId: result.data.gameId, status: result.data.status, error: '' });
            })
            .catch(error => {
                this.setState({ error: error.response.data.message })
            });
    }

    move(i) {
        const { gameId, status } = this.state;

        const squares = this.state.squares.slice();
        if (squares[i] || status !== -1) {
            return;
        }
        squares[i] = "X";
        let move = i;
        axios.get('/move', { params: { gameId, move } })
            .then((result) => {
                this.setState({ status: result.data.status, error: '' });
                if (result.data.status === 2 || result.data.status === -1)
                    squares[result.data.move.move] = "O";
                this.setState({
                    squares: squares
                });
            })
            .catch(error => {
                this.setState({ error: error.response.data.message })
            });


    }

    render() {
        const { playerName, playerId, error, squares, status } = this.state;

        return (
            <Container>
                <Row>
                    <Col sm={10}>
                        <h1>Tic-Tac-Toe</h1>
                    </Col>
                </Row>
                {(error !== '') ? (
                    <Row>
                        <Col sm={10}>
                            <Alert color="danger">{error}</Alert>
                        </Col>
                    </Row>
                ) : null}

                <Row>
                    <Col sm={10}>
                        <AvForm onValidSubmit={this.onSubmit}>
                            <AvGroup row>
                                <Col sm={5}>
                                    <AvInput required type="text" name="playerName" id="playerName" value={playerName} onChange={this.onChange} placeholder="Enter player name" />
                                    <AvFeedback>Player name is required!</AvFeedback>
                                </Col>
                                <Col sm={5}>
                                    <Button type="submit" color="primary" outline>START</Button>
                                </Col>
                            </AvGroup>
                        </AvForm>
                    </Col>
                </Row>
                {(playerId !== -1) ? (
                    <div>
                        {(status === 1) ? (
                            <Row>
                                <Col sm={10}>
                                    <Alert color="success"><h6>You Won!</h6></Alert>
                                </Col>
                            </Row>
                        ) : null}
                        {(status === 2) ? (
                            <Row>
                                <Col sm={10}>
                                    <Alert color="primary"><h6>Machine Won!</h6></Alert>
                                </Col>
                            </Row>
                        ) : null}
                        {(status === 0) ? (
                            <Row>
                                <Col sm={10}>
                                    <Alert color="secondary"><h6>No One Wins!</h6></Alert>
                                </Col>
                            </Row>
                        ) : null}

                        <div className="game">
                            <div className="game-board">
                                <Board
                                    squares={squares}
                                    onClick={i => this.move(i)}
                                />
                            </div>
                            <div className="game-info">
                                <Row>
                                    <h6>X - Player</h6>
                                </Row>
                                <Row>
                                    <h6> O - Machine</h6>
                                </Row>
                            </div>

                        </div>
                    </div>

                ) : null}

            </Container>
        );
    }
}



render(<Game />, document.getElementById('root'));




