package br.ce.wcaquino.entidades;

public class Calculadora {

    public double somar (int a , int b){
        return a + b;
    }
    public double subtrair(int a, int b){
        return a-b;
    }

    public double divide(int a, int b) {
        try{
            return a/b;
        }catch(ArithmeticException ex){
            throw new ArithmeticException("zero can't be divided");
        }

    }
}
