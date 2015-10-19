package com.neet.cf.handlers;

public class OverworldGrid
{
	private int[][] grid;
	private int height;
	public OverworldGrid(){};
	public OverworldGrid(int width, int height)
	{
		this.height=height;
		grid = new int[width][height];
	}
	public int getPos(int x, int y)
	{
		return grid[y][x];
	}
	public void setPos(int x, int y, int set)
	{
		 grid[y][x]=set;
	}
}
