package com.wipro.tictactoe;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

import com.wipro.tictactoe.service.TicTacToeService;

@SpringBootTest
class TicTacToeServiceTest {

	@Autowired
	TicTacToeService service;

	@Test
	public void testAdd() {
		assertThat(service.add(1, 2)).isEqualTo(3);
	}

}
