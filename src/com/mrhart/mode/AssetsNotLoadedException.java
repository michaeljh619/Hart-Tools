package com.mrhart.mode;

public class AssetsNotLoadedException extends Exception {
	private static final long serialVersionUID = 7954065615920503893L;

	public AssetsNotLoadedException(){
		super("AssetManager assets was not completely loaded when MetaMode returned true.");
	}
}
