/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package util;

public class Conversao {

  /* ***************************************************************
  Metodo: bitsParaAscii*
  Funcao: Transforma os bits da mensagem em caracteres ASCII*
  Parametros: int[] fluxoBrutoDeBits: bits a serem convertidos*
  Retorno: int[] quadro: vetor com os numeros ASCII*
  *************************************************************** */
  public static int[] bitsParaAscii(int[] fluxoBrutoDeBits) {
    int tamanho = 0;

    //pegando tamanho do array de quadros
    for(int i=0; i<fluxoBrutoDeBits.length; i++){
      if(i%8 == 7){
        tamanho++;
      }
    }

    int[] quadro = new int[tamanho];
    int[] temp = Conversao.inverter(fluxoBrutoDeBits); //reverte o array
    int decimal = 0;
    for(int i=0, j=0, k=0; i<fluxoBrutoDeBits.length; i++){ //transforma em decimal
      decimal += temp[i]*Math.pow(2, j);
      j++;

      if(i%8 == 7){
        quadro[k] = decimal;
        decimal = 0;
        j = 0;
        k++;
      }
    }

    return quadro;
  }

  /* ***************************************************************
  Metodo: asciiParaBits*
  Funcao: Transforma os numeros ASCII para bits*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: int[] bitsBrutos: vetor com os bits*
  *************************************************************** */
  public static int[] asciiParaBits(int[] quadro) {

    //calcula o tamanho do vetor fluxoBrutoDeBits
    int novoTamanho = 0;
    if(quadro.length%4 == 0){
      novoTamanho = quadro.length/4;
    }else{
      novoTamanho = (quadro.length/4)+1;
    }

    int[] fluxoBrutoDeBits = new int[novoTamanho];
    int valor = 0; //valor com capacidade para armazenar ate 4 numeros

    for(int i=0, pos=0; i<quadro.length; i++){
      valor <<= 8; //desloca 8 bits para esquerda
      valor = valor | quadro[i]; //recebe em binario o valor de quadro[i]

      if(i%4 >= 0 && i == quadro.length-1){ //caso o vetor tenha menos de 4 numeros
        fluxoBrutoDeBits[pos] = valor;
      }else if(i%4 == 3){ //a cada 4 iteracoes
        fluxoBrutoDeBits[pos] = valor;
        valor=0; //reseta a variavel
        pos++;
      }
    }

    return fluxoBrutoDeBits;
  }

  /* ***************************************************************
  Metodo: asciiParaMensagem*
  Funcao: Transforma os numeros ASCII para Mensagem*
  Parametros: int[] quadro: vetor com os numeros ASCII*
  Retorno: String strMensagem: mensagem*
  *************************************************************** */
  public static String asciiParaMensagem(int[] quadro) {
    StringBuilder strMensagem = new StringBuilder();

    for(int i=0; i<quadro.length; i++){
       strMensagem.append(Character.toString((char)quadro[i]));
    }

    return strMensagem.toString();
  }

  /* ***************************************************************
  Metodo: bitsParaString*
  Funcao: Transforma os os bits da mensagem para o tipo String*
  Parametros: int[] bits: vetor com os bits*
  Retorno: String strBits: String com os bits*
  *************************************************************** */
  public static String bitsParaString(int[] bits) {
    StringBuilder strBits = new StringBuilder();
    strBits.append(bits[0]);

    for(int i=1; i<bits.length; i++){
      if(i % 8 == 0){
        strBits.append(" ");
        strBits.append(bits[i]);
      }
      else{
        strBits.append(bits[i]);
      }
    }

    return strBits.toString();
  }

  /* **************************************************************
  Metodo: asciiParaString*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar
          os bits em uma string seguido da letra ou numero rspectivo
          para imprimir na tela*
  Parametros: int[] quadro: vetor com os numeros em ASCII
              int tipoDeImpressao: em qual caixa de texto sera impresso*
  Retorno: void*
  *************************************************************** */
  public static String asciiParaString(int[] quadro, int tipoDeFormato) {
    StringBuilder strAscii = new StringBuilder();

    if(tipoDeFormato == 0){ //tipoDeFormato == ASCII
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]);
        }
        else{
          strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]+" ");
        }
      }
    }
    else{ //tipoDeFormato == ASCII_DECODIFICADO
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i]));
        }
        else{
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i])+" ");
        }
      }
    }

    return strAscii.toString();
  }

  /* ***************************************************************
  Metodo: inverter*
  Funcao: inverte o elementos de um array*
  Parametros: int[] bits: vetor com os bits*
  Retorno: int[] temp: vetor com os elementos invertidos*
  *************************************************************** */
  private static int[] inverter(int[] bits) {
    int[] temp = new int[bits.length];
    for(int i=bits.length-1, j=0; i>=0; i--){
      temp[j] = bits[i];
      j++;
    }

    return temp;
  }
}