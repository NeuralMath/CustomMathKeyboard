package com.example.mathieu.custommathkeyboard;

/**
 * Created by Mathieu on 2017-04-23.
 */

public class ReplacedChar {

    private int position;
    private char oldChar;
    private char newChar;

    public ReplacedChar() {
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public char getOldChar() {
        return oldChar;
    }

    public void setOldChar(char oldChar) {
        this.oldChar = oldChar;
    }

    public char getNewChar() {
        return newChar;
    }

    public void setNewChar(char newChar) {
        this.newChar = newChar;
    }
}
