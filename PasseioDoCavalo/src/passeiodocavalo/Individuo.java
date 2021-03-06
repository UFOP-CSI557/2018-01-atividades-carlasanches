/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passeiodocavalo;

import java.util.ArrayList;
import java.util.Random;
import sun.util.locale.provider.HostLocaleProviderAdapterImpl;

/**
 *
 * @author Carla
 */
public class Individuo implements Comparable<Individuo>{
    
    private int tabuleiro[][];
    private int tabuleiroSaida[][];
    
    //Genotipo
    private ArrayList<Integer> cromossomos;
    //Fenotipo
    private ArrayList<Integer> variaveis;
    //Decodificado - binario para inteiro
    private ArrayList<Integer> decodificacao;
    //Custo da função objetivo
    Integer funcaoObjetivo;
    
    //Precisão - numero de bits por variavel
    Integer precisao;
    //Valor minimo
    Integer minimo;
    //Valor máximo
    Integer maximo;
    
    //Numero de variaveis
    Integer nVar;

    public Individuo(Integer precisao, Integer minimo, Integer maximo, Integer nVar) {
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.cromossomos = new ArrayList<>();       
        this.tabuleiro = new int[this.maximo+1][this.maximo+1];
        this.tabuleiroSaida = new int[this.maximo+1][this.maximo+1];
    }

    public ArrayList<Integer> getCromossomos() {
        return cromossomos;
    }

    public void setCromossomos(ArrayList<Integer> cromossomos) {
        this.cromossomos = cromossomos;
    }

    public ArrayList<Integer> getVariaveis() {
        return variaveis;
    }

    public void setVariaveis(ArrayList<Integer> variaveis) {
        this.variaveis = variaveis;
    }

    public ArrayList<Integer> getDecodificacao() {
        return decodificacao;
    }

    public void setDecodificacao(ArrayList<Integer> decodificacao) {
        this.decodificacao = decodificacao;
    }

    public Integer getFuncaoObjetivo() {
        return funcaoObjetivo;
    }

    public void setFuncaoObjetivo(Integer funcaoObjetivo) {
        this.funcaoObjetivo = funcaoObjetivo;
    }

    public Integer getPrecisao() {
        return precisao;
    }

    public void setPrecisao(Integer precisao) {
        this.precisao = precisao;
    }

    public Integer getMinimo() {
        return minimo;
    }

    public void setMinimo(Integer minimo) {
        this.minimo = minimo;
    }

    public Integer getMaximo() {
        return maximo;
    }

    public void setMaximo(Integer maximo) {
        this.maximo = maximo;
    }

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
    }

    public int[][] getTabuleiroSaida() {
        return tabuleiroSaida;
    }
    
    
    
    //Gerar o genotipo
    public void criar(){
        this.cromossomos = new ArrayList<>();
        
        Random rnd = new Random();
        
        for(int i = 0; i < this.getnVar(); i++){
            for(int j = 0; j < this.getPrecisao(); j++){
                this.getCromossomos().add(rnd.nextInt(2)); // gera 0 ou 1
            }
        }
    }
    
    //Genotipo -> Fenotipo: Binário -> Real
    public void decodificar(){
        
        this.decodificacao = new ArrayList<>();
        this.variaveis = new ArrayList<>();
                
        int valor;
        Double real = null;
        
        for(int i = 0; i < this.getnVar(); i++){
            
            valor = 0;
            
            //Calcula para qual posiçao o cavalo andou
            for(int j = 0; j < this.getPrecisao(); j++){                
                valor += Math.pow(2, this.getPrecisao() - j - 1) * this.getCromossomos().get(i * this.getPrecisao() + j);
            }
            
            //guarda todas as posições selecionadas.
            this.getDecodificacao().add(valor);            
        //    real = (valor * (this.getMaximo() - this.getMinimo()) / (Math.pow(2, this.getPrecisao())- 1.0)) + this.getMinimo();            
        } 
    }
    
    public void calcularMovimento(){
      //  Random rnd = new Random();
        
        int pos = 0;
                
        for(int i = 0; i <= this.maximo; i++){
            for(int j = 0; j <= this.maximo; j++){
                tabuleiro[i][j] = pos++;             
            }
        } 
        
        for(int i = 0; i <= this.maximo; i++){
            for(int j = 0; j <= this.maximo; j++){
                tabuleiroSaida[i][j] = -1;             
            }
        }
// 
        int movimentoX[] = { 1,  2, 2, 1, -1, -2, -2, -1};
        int movimentoY[] = {-2, -1, 1, 2,  2,  1, -1, -2};
        
        int proximoX = 0;
        int proximoY = this.getDecodificacao().get(0);
        
        this.getVariaveis().add(proximoY);
        tabuleiroSaida[proximoX][proximoY] = 0;
        
        for(int i = 1; i < this.getDecodificacao().size(); i++){
            
            proximoX += movimentoX[this.getDecodificacao().get(i)];
            proximoY += movimentoY[this.getDecodificacao().get(i)];
            
            if(proximoX >= this.minimo && proximoY >= this.minimo && proximoX <= this.maximo && proximoY <= this.maximo){
                if(!this.getVariaveis().contains(tabuleiro[proximoX][proximoY])){
                    this.getVariaveis().add(tabuleiro[proximoX][proximoY]);
                    
                    if(!this.getVariaveis().contains(-1)){
                        tabuleiroSaida[proximoX][proximoY] = i;
                    }                    
                }      
                else{
                    this.getVariaveis().add(-1);
                }
            }
            else{
                this.getVariaveis().add(-1);
            }
        }
    }

    @Override
    public int compareTo(Individuo o) {
        return this.getFuncaoObjetivo().compareTo(o.getFuncaoObjetivo());
    }
}
