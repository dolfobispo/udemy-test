package br.ce.wcaquino.servicos;



import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.utils.DataUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.exceptions.FilmeSemEstoqueException;
import br.ce.wcaquino.exceptions.LocadoraException;

public class LocacaoServiceTest {
    private LocacaoService service;
    private List<Filme> filmes;
    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setupService(){
        service = new LocacaoService();
        Filme starWars = new Filme("Star Wars", 1, 4.0);
        Filme coringa = new Filme("Coringa", 1, 2.5);
        filmes  = Arrays.asList(starWars,coringa);
    }
    @Test
    public void deveAlugarFilme() throws Exception {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme starWars = new Filme("Star Wars", 1, 4.0);
        Filme coringa = new Filme("Coringa", 1, 2.5);
        List<Filme> filmes  = Arrays.asList(starWars,coringa);

        //acao
        Locacao locacao = service.alugarFilme(usuario, filmes);

        //verificacao

        error.checkThat(locacao.getValor(), is(equalTo(6.5)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));


    }

    @Test(expected = FilmeSemEstoqueException.class)
    public void deveLancarExcecaoAlugarFilmeSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
        //cenario
        service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme starWars = new Filme("Star Wars", 1, 4.0);
        Filme coringa = new Filme("Coringa", 0, 2.5);

        List<Filme> filmes  = Arrays.asList(starWars,coringa);

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void deveLancarExcecaoUsuarioVazio() throws FilmeSemEstoqueException{
        //cenario
        service = new LocacaoService();


        //acao
        try {
            service.alugarFilme(null, filmes);
            Assert.fail();
        } catch (LocadoraException e) {
            assertThat(e.getMessage(), is("Usuario vazio"));
        }
    }


    @Test
    public void naoDeveAlugarFilmeSemFilme() throws FilmeSemEstoqueException, LocadoraException{
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");

        exception.expect(LocadoraException.class);
        exception.expectMessage("Filme vazio");

        //acao
        service.alugarFilme(usuario, null);
    }

    @Test
    public void devePagar75PorCentoTerceiroFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
        new Filme("Filme 2",2,4.0), new Filme("Filme 3",2,4.0));

        Locacao locacao = service.alugarFilme(usuario,filmes);

        //verificacao
        assertThat(locacao.getValor(),is(11.0));
    }
    @Test
    public void devePagar50PorCentoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0), new Filme("Filme 3",2,4.0),
                new Filme("Filme 4",2,4.0));

        Locacao locacao = service.alugarFilme(usuario,filmes);

        //verificacao
        assertThat(locacao.getValor(),is(13.0));
    }
    @Test
    public void devePagar25PorCentoQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0), new Filme("Filme 3",2,4.0),
                new Filme("Filme 4",2,4.0), new Filme("Filme 5",2,4.0));

        Locacao locacao = service.alugarFilme(usuario,filmes);

        //verificacao
        assertThat(locacao.getValor(),is(14.0));
    }
    @Test
    public void devePagarZeroQuartoFilme() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 1",2,4.0),
                new Filme("Filme 2",2,4.0), new Filme("Filme 3",2,4.0),
                new Filme("Filme 4",2,4.0), new Filme("Filme 5",2,4.0),
                new Filme("Filme 6",2,4.0));

        Locacao locacao = service.alugarFilme(usuario,filmes);

        //verificacao
        assertThat(locacao.getValor(),is(14.0));
    }

    @Test
    public void naoDeveDevolverFilmeNoDomingo() throws FilmeSemEstoqueException, LocadoraException {
        Usuario usuario = new Usuario("Usuario 1");
        List<Filme> filmes = Arrays.asList(new Filme("Filme 2",2,4.0));

        Locacao locacao = service.alugarFilme(usuario,filmes);
        boolean dia = DataUtils.verificarDiaSemana(locacao.getDataRetorno(),5);
        assertThat(dia,is(false));
    }
}
