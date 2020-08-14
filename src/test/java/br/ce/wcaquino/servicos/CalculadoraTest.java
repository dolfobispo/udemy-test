package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Calculadora;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculadoraTest {
    int a;
    int b;
    Calculadora cal;
    @Before
    public void setup(){
        //cenario
        a=5;
        b=3;
        cal = new Calculadora();
    }
    @Test
    public void deveSomarDoisValores(){
        // acao
        double resultado = cal.somar(a,b);
        //verificacao
        Assert.assertEquals(8,resultado,0.1);

    }
    @Test
    public  void deveSubtrairDoisValores(){
        double resultado = cal.subtrair(a,b);
        //verificacao
        Assert.assertEquals(2,resultado,0.1);
    }

    @Test
    public void deveDividirDoisValores(){
        a = 6;
        b =3;
        // ação
        double resultado = cal.divide(a,b);
        //verificação
        Assert.assertEquals(2,resultado,0.1);
    }
    @Test(expected = ArithmeticException.class)
    public void deveValidarDivisaoPorZero(){
        a = 6;
        b =0;
        // ação
        double resultado = cal.divide(a,b);

    }
}
