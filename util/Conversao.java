/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
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
    int[] temp = Conversao.reverter(fluxoBrutoDeBits); //reverte o array
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
    int[] bitsBrutos = new int[quadro.length*8];

    for(int i=0, j=0; i<quadro.length; i++){
      Conversao.converter(quadro[i], bitsBrutos, j);
      j+=8;
    }

    return Conversao.reverter(bitsBrutos);
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

    int i=1;
    for(; i<bits.length; i++){
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

  private static void converter(int numero, int[] bitsBrutos, int index) {
    if(numero > 0){
      bitsBrutos[index] = numero%2;
      index++;

      converter(numero >> 1, bitsBrutos, index);
    }
  }

  private static int[] reverter(int[] bits) {
    int[] temp = new int[bits.length];
    for(int i=bits.length-1, j=0; i>=0; i--){
      temp[j] = bits[i];
      j++;
    }

    return temp;
  }
}