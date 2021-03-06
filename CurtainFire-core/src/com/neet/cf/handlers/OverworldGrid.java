package com.neet.cf.handlers;

public class OverworldGrid
{
	private int[][] grid;
	private int height;
	public OverworldGrid(){};
	public OverworldGrid(int width, int height)
	{
		this.height=height;
		grid = new int[height][width];
	}
	public int getPos(int x, int y)
	{
		return grid[height-1-x][y];
	}
	public void setPos(int x, int y, int set)
	{
		 grid[height-1-x][y]=set;
	}
}
