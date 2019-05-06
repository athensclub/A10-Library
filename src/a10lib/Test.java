package a10lib;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import a10lib.compiler.token.Tokenizer;
import a10lib.util.FileUtils;
import a10lib.util.Images;

public class Test {
    
    public static void main(String[] args) throws Exception {
	new Tokenizer() {
	    
	    @Override
	    protected char nextCharInStream() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	    }
	    
	    @Override
	    public boolean eof() {
		// TODO Auto-generated method stub
		return false;
	    }
	};
    }

}
