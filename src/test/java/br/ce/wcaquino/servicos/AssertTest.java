package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class AssertTest {
    @Rule
    public ErrorCollector erro = new ErrorCollector();
    @Test
    public void test(){
        assertEquals(2,2);
        assertEquals(0.512,0.51,0.01);
        assertNotEquals("bola","casa");
        Usuario u1 = new Usuario("rodolfo");
        Usuario u2 = new Usuario("rodolfo");
        assertEquals(u1,u2);
        assertSame(u1,u1);

        assertThat(2, CoreMatchers.not(3));
        assertEquals(0.512,0.51,0.01);
        assertNotEquals("bola","casa");
        assertEquals(u1,u2);
        assertSame(u1,u1);

        erro.checkThat(2,is(3));
        erro.checkThat(2,not(2));


    }


}
