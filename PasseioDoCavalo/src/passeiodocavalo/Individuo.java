/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package passeiodocavalo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Carla
 */
public class Individuo implements Comparable<Individuo>{
    
    private int tabuleiro[][];
    
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
    Double minimo;
    //Valor máximo
    Double maximo;
    
    //Numero de variaveis
    Integer nVar;

    public Individuo(Integer precisao, Double minimo, Double maximo, Integer nVar) {
        this.precisao = precisao;
        this.minimo = minimo;
        this.maximo = maximo;
        this.nVar = nVar;
        this.cromossomos = new ArrayList<>();
        this.tabuleiro = new int[this.nVar][this.nVar];
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

    public Double getMinimo() {
        return minimo;
    }

    public void setMinimo(Double minimo) {
        this.minimo = minimo;
    }

    public Double getMaximo() {
        return maximo;
    }

    public void setMaximo(Double maximo) {
        this.maximo = maximo;
    }

    public Integer getnVar() {
        return nVar;
    }

    public void setnVar(Integer nVar) {
        this.nVar = nVar;
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
            calcularMovimento();
        //    real = (valor * (this.getMaximo() - this.getMinimo()) / (Math.pow(2, this.getPrecisao())- 1.0)) + this.getMinimo();
            
        }        
    }
    
    public void calcularMovimento(){
        Random rnd = new Random();
        
        int pos = 0;
                
        for(int i = 0; i < this.nVar; i++){
            for(int j = 0; j < this.nVar; j++){
                tabuleiro[i][j] = pos++;             
            }
        }  
        
        int xInicio = rnd.nextInt(this.nVar);
        int yInicio = rnd.nextInt(this.nVar);
        
        this.getVariaveis().add(tabuleiro[xInicio][yInicio]);     

        //tentar "normalizar" para não passar do tabuleiro

        int movimentoX[] = { 1,  2, 2, 1, -1, -2, -2, -1};
        int movimentoY[] = {-2, -1, 1, 2,  2,  1, -1, -2};
        
        for(int i = 0; i < this.getDecodificacao().size(); i++){
            int proximoX = movimentoX[this.getDecodificacao().get(i)];
            int proximoY = movimentoY[this.getDecodificacao().get(i)];
            
            if(proximoX > 0 && proximoY > 0 && proximoX < 64 && proximoY < 64){
                if(!this.getVariaveis().contains(tabuleiro[proximoX][proximoY])){
                    this.getVariaveis().add(tabuleiro[proximoX][proximoY]);
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
