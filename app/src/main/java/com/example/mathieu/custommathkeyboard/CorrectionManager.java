package com.example.mathieu.custommathkeyboard;

import java.util.ArrayList;

/**
 * Created by Mathieu on 2017-04-20.
 */

public class CorrectionManager {

    private String equation;
    private ArrayList<ReplacedChar> replacedCharList;

    private ArrayList<Integer> indexList; //index of the deleted char that did not got replaced
    private int correctionCounter; //A counter that count the corrected character

    public CorrectionManager(String equation) {
        this.equation = equation;
        correctionCounter = 0;
        replacedCharList = new ArrayList<>();
        indexList = new ArrayList<>();
    }

    public int getCorrectionCounter() {
        return correctionCounter;
    }

    public void deleteChar(Character character, int index){
        ReplacedChar temp = new ReplacedChar();
        temp.setOldChar(character);
        temp.setPosition(index);

        replacedCharList.add(temp);
        indexList.add(index);
        correctionCounter++;
    }

    public  void addChar(Character character, int index){
        int deletedChar = 0;//count the unreplaced char before this one;
        for (int i = 0; i < indexList.size(); i++){
            if(indexList.get(i) < index)
                deletedChar++;
        }


        for(int i = 0; i < replacedCharList.size(); i++){
            if(replacedCharList.get(i).getPosition() - deletedChar == index){
                replacedCharList.get(i).setNewChar(character);
                removeFromList(indexList, index + deletedChar);
                break;
            }
        }
        correctionCounter--;
    }

    private void removeFromList(ArrayList<Integer> list, int value){
        for (int i = 0; i < list.size(); i++)
            if(list.get(i) == value)
                list.remove(i);
    }

    /**
     * This method find if there is a deleted char with no replacement yet at a position
     * @param position the position of the char
     * @return
     */
    public boolean hasDeletedCharAt(int position){
        int deletedChar = 0;//count the unreplaced char before this one;
        for (int i = 0; i < indexList.size(); i++){
            if(indexList.get(i) < position)
                deletedChar++;
        }

        for (int i = 0; i < indexList.size(); i++)
            if(position + deletedChar == indexList.get(i))
                return true;

        return false;
    }

    /**
     * This method find if there is succession of deleted char with no replacement yet at a position
     * @param position the position of the first char
     * @param length the length of the succession
     * @return
     */
    public boolean hasDeletedCharAt(int position, int length){
        for (int i = 0; i < length; i++)
            if(!hasDeletedCharAt(position + i))
                return false;

        return true;
    }
}
