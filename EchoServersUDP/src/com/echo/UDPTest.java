package com.echo;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

class UDPTest {

	EchoClient client;
	@Before
	public void setup() {
		new EchoServer().start();
	}
	@Test
	void test() throws IOException {
		String echo=client.sendEcho("hello server");
		assertEquals("hello server", echo);
		echo=client.sendEcho("server is working");
		assertFalse(echo.equals("hello server"));
	}
	@After
	public void tearDown() throws IOException {
		client.sendEcho("end");
		client.close();
	}

}
