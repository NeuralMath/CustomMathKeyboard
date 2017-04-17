package com.example.mathieu.custommathkeyboard;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This object keep in memory the equation in a format that the equation resolver will be able to understand.
 * Created by Mathieu Boucher on 2017-04-17.
 */

public class EquationManager {

    private String equation;

    private ArrayList<Integer> exponentsIndex; //index of all the exponent character

    /**
     *  @param eq the starting equation
     */
    public EquationManager(String eq) {
        equation = eq;
        exponentsIndex = new ArrayList<>();
    }

    public String getEquation() {
        int addedChar = 0; // number of added char;
        StringBuilder finalEquation = new StringBuilder(equation);
        if(exponentsIndex.size() == 1){
            finalEquation.insert(exponentsIndex.get(0), "^(");
            finalEquation.insert(exponentsIndex.get(0) + 3, ")");
        }else{
            for(int i = 0; i < exponentsIndex.size(); i++){
                finalEquation.insert(exponentsIndex.get(i) + addedChar, "^(");
                addedChar += 2;
                while (i != exponentsIndex.size() - 1 && exponentsIndex.get(i+1) - exponentsIndex.get(i) == 1)
                    i++;
                finalEquation.insert(exponentsIndex.get(i) + addedChar + 1, ")");
                addedChar++;
            }
        }
        return finalEquation.toString();
    }

    public void setEquation(String eq) {
        equation = eq;
        exponentsIndex = new ArrayList<>();
    }

    public void addExponent(int index, String exponent){
        exponentsIndex.add(index);
        StringBuilder sb = new StringBuilder(equation);
        sb.insert(index, exponent);
        equation = sb.toString();

        sortExponentsIndex();
    }

    public void insert(int index, String text){
        StringBuilder sb = new StringBuilder(equation);
        sb.insert(index, text);
        equation = sb.toString();
    }

    private void sortExponentsIndex(){
        int[] array = new int[exponentsIndex.size()];
        for (int i = 0; i < array.length; i++)
            array[i] = exponentsIndex.get(i);
        Arrays.sort(array);
        exponentsIndex = new ArrayList<>();
        for (int i = 0; i < array.length; i++)
            exponentsIndex.add(array[i]);
    }

    public void deleteChar(int index){
        for (int i = 0; i < exponentsIndex.size(); i++){
            if(index == exponentsIndex.get(i)){
                exponentsIndex.remove(i);
                sortExponentsIndex();
                break;
            }
        }
        if(index > 0){
            StringBuilder sb = new StringBuilder(equation);
            sb.deleteCharAt(index - 1);
            equation = sb.toString();
        }
    }
}
