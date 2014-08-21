package com.github.kd.core.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class RoutingTable {

	private List<List<Node>> kBuckets;

	public RoutingTable(int capacity) {
		kBuckets = new ArrayList<List<Node>>(capacity);
	}

	public void addNode(Node node) {

	}

	public List<Node> getClosestTo(Node node) {
		return new LinkedList<Node>();
	}
}
