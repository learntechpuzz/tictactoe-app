import React, { Component } from 'react';
import './App.css';
import { Form, FormGroup, Label, Input, Button, Container, Row } from 'reactstrap';
import axios from 'axios'


class App extends Component {
  constructor() {
    super();
    this.state = {
      number1: '',
      number2: '',
      result: '',
    };
  }
  onChange = (e) => {
    this.setState({ [e.target.name]: e.target.value });
  }

  onSubmit = (e) => {
    e.preventDefault();
    const { number1, number2 } = this.state;
    axios.get('/add', { params: { number1, number2 } })
      .then((result) => {
        this.setState({result: result.data});
      })
      .catch(error => {
        return error;
      });
  }

  render() {
    const { number1, number2, result } = this.state;
    return (
      <Container>
        <Row><h1>Tic-Tac-Toe</h1></Row>
        <Form onSubmit={this.onSubmit}>
          <FormGroup>
            <Label for="number1">Number 1</Label>
            <Input type="number" name="number1" id="number1" value={number1} onChange={this.onChange} placeholder="Enter Number1" />
          </FormGroup>
          <FormGroup>
            <Label for="number2">Number 2</Label>
            <Input type="number" name="number2" id="number2" value={number2} onChange={this.onChange} placeholder="Enter Number2" />
          </FormGroup>
          <FormGroup>
            <Label for="result">Result</Label>
            <Input type="number" name="result" id="result" readOnly value={result} onChange={this.onChange}/>
          </FormGroup>
          <Button type="submit" color="primary">ADD</Button>
        </Form>
      </Container>
    );
  }
}


export default App;
