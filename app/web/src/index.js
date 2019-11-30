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
            squares: Array(9).fill(null),
            stepNumber: -1,
            xIsNext: true,
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

    handleClick(i) {
        const squares = this.state.squares.slice();
        if (squares[i]) {
            return;
        }
        squares[i] = this.state.xIsNext ? "X" : "O";
        this.setState({
            squares: squares,
            xIsNext: !this.state.xIsNext
        });
    }

    render() {
        const { playerName, playerId, error, squares } = this.state;

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
                        <Row>
                            <Col sm={10}>
                                <div className="game">
                                    <div className="game-board">
                                        <Board
                                            squares={squares}
                                            onClick={i => this.handleClick(i)}
                                        />
                                    </div>
                                    <div className="game-info">
                                        <row>
                                            <h6>X - Player</h6>
                                        </row>
                                        <row>
                                            <h6> O - Machine</h6>
                                        </row>
                                    </div>

                                </div>
                            </Col>
                        </Row>

                    </div>

                ) : null}

            </Container>
        );
    }
}



render(<Game />, document.getElementById('root'));




