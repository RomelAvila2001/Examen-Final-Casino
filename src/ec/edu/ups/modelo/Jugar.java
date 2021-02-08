/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.ups.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NANCY
 */
public class Jugar implements Runnable{

    private Jugador jugador;
    private Banca banca;
    private int dineroApuesta;
    private int numeroDePartida;
    int contadorDineroApuesta=0;
    private List<Apostar> apuestas;

    public Jugar(Jugador jugador, Banca banca, int numeroDePartida) {
        this.jugador = jugador;
        this.banca = banca;
        this.numeroDePartida = numeroDePartida;
        this.dineroApuesta=10;
        apuestas = new ArrayList();
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Banca getBanca() {
        return banca;
    }

    public void setBanca(Banca banca) {
        this.banca = banca;
    }

    public int getDineroApuesta() {
        return dineroApuesta;
    }

    public void setDineroApuesta(int dineroApuesta) {
        this.dineroApuesta = dineroApuesta;
    }

    public int getNumeroDePartida() {
        return numeroDePartida;
    }

    public void setNumeroDePartida(int numeroDePartida) {
        this.numeroDePartida = numeroDePartida;
    }

    public int getContadorDineroApuesta() {
        return contadorDineroApuesta;
    }

    public void setContadorDineroApuesta(int contadorDineroApuesta) {
        this.contadorDineroApuesta = contadorDineroApuesta;
    }

    public List<Apostar> getApuestas() {
        return apuestas;
    }

    public void setApuestas(List<Apostar> apuestas) {
        this.apuestas = apuestas;
    }
    
    public boolean EnQuieba(){
        return jugador.getSaldo() >=10;
    }
    
    public synchronized void sumarApuesta(Apostar apostar){
        apuestas.add(apostar);
    }
    
    public void DisminuirDineroJugador(int saldo){
        int SaldoN = jugador.getSaldo() - saldo;
        jugador.setSaldo(SaldoN);
    }
    
    public int apostarNumero(){
        int NumeroAlazar = (int)(Math.random()*36 +1);
        DisminuirDineroJugador(10);
        banca.setSaldo(banca.getSaldo()+10);
        return NumeroAlazar;
    }
    
    public void aumentarSaldoJugador(int saldo){
        int SaldoN = jugador.getSaldo() + saldo;
        jugador.setSaldo(SaldoN);
        jugador.setVictorias(jugador.getVictorias()+1);
    }
    
    public void numeroGanador(){
        int saldoBancaMomento = banca.aumentarSaldo(Thread.currentThread());
        aumentarSaldoJugador(saldoBancaMomento);
        banca.setSaldo(banca.getSaldo()-saldoBancaMomento);    
    }
    
    public String apostarPARIMPAR(){
        try {
            boolean par = Random.class.newInstance().nextBoolean();
            DisminuirDineroJugador(10);
            banca.setSaldo(banca.getSaldo()+10);
            if(par){
                return "true";
            }else{
                return "false";
            }
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(Jugar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "false";
    }
    
    public int apostarMartingala(){
        int NumeroAlazar = (int)(Math.random()*36 +1);
        DisminuirDineroJugador(getDineroApuesta());
        banca.setSaldo(banca.getSaldo()+getDineroApuesta());
        return NumeroAlazar;
    }
    
    public void perderMartingala(){
        setDineroApuesta(getDineroApuesta()*2);
    }
    
    @Override
    public void run() {
        if(EnQuieba()){
            Apostar apuesta = new Apostar();
            switch (jugador.getTipoapuesta()){
                case("NUMERO") -> {
                    int numeroRuleta = apostarNumero();
                    jugador.setNumeroapostado(String.valueOf(numeroRuleta));
                    if(Integer.valueOf(jugador.getNumeroruleta())== numeroRuleta){
                        numeroGanador();
                        apuesta.setCantidadapostada(10);
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Jugador");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(String.valueOf(numeroRuleta));
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }else{
                        jugador.setDerrotas(jugador.getDerrotas()+1);
                        apuesta.setCantidadapostada(10);
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Banca");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(String.valueOf(numeroRuleta));
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }
                    apuesta.setNumeropartida(numeroDePartida);
                    apuesta.setSaldojugador(jugador.getSaldo());
                    apuesta.setSaldobanca(banca.getSaldo());
                    sumarApuesta(apuesta);
                }
                case ("PARIMPAR") -> {
                    String valor = (String.valueOf(apostarPARIMPAR()));
                    if(Integer.valueOf(jugador.getNumeroruleta())%2==0 && valor.equals("true")){
                        numeroGanador();
                        apuesta.setCantidadapostada(10);
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Jugador");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(valor);
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }else{
                        jugador.setDerrotas(jugador.getDerrotas()+1);
                        apuesta.setCantidadapostada(10);
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Banca");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(valor);
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }
                    apuesta.setNumeropartida(numeroDePartida);
                    apuesta.setSaldojugador(jugador.getSaldo());
                    apuesta.setSaldobanca(banca.getSaldo());
                    sumarApuesta(apuesta);
                }
                default -> {
                    int NumeroRuletaMartingala = apostarMartingala();
                    jugador.setNumeroapostado(String.valueOf(NumeroRuletaMartingala));
                    if(Integer.valueOf(jugador.getNumeroruleta())== NumeroRuletaMartingala){
                        numeroGanador();
                        apuesta.setCantidadapostada(this.getDineroApuesta());
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Jugador");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(String.valueOf(NumeroRuletaMartingala));
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }else{
                        perderMartingala();
                        jugador.setDerrotas(jugador.getDerrotas()+1);
                        apuesta.setCantidadapostada(this.getDineroApuesta()/2);
                        apuesta.setCodigoJugadorFk(jugador);
                        apuesta.setGanador("Banca");
                        apuesta.setNumeroganador(jugador.getNumeroruleta());
                        apuesta.setNumeroapostado(String.valueOf(NumeroRuletaMartingala));
                        apuesta.setTipoapuesta(jugador.getTipoapuesta());
                        apuesta.setNumeropartida(getNumeroDePartida());
                        apuesta.setSaldobanca(banca.getSaldo());
                        apuesta.setSaldojugador(jugador.getSaldo());
                        //System.out.println(apuesta);
                    }
                    apuesta.setNumeropartida(numeroDePartida);
                    apuesta.setSaldojugador(jugador.getSaldo());
                    apuesta.setSaldobanca(banca.getSaldo());
                    sumarApuesta(apuesta);
                }
            }
        }
    }
    
}
