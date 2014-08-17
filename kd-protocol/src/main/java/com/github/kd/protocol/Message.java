package com.github.kd.protocol;

public interface Message {

	MessageType getType();

	MessageCommand getCommand();

	int length();
}
