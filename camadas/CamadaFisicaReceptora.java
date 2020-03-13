/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas;

import view.PainelEsquerdo;
import view.TelaPrincipal;
import util.Conversao;

public class CamadaFisicaReceptora {
  private static int[] fluxoBrutoDeBits;

  /* *****************************************************************************
  Metodo: camadaFisicaReceptora*
  Funcao: Enviar o vetor com os numeros da tabela ASCII para a camada de aplicacao
          receptora*
  Parametros: int[] fluxoBrutoDeBitsPontoB: vetor com os bits recebidos*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaFisicaReceptora(int[] fluxoBrutoDeBitsPontoB) {
    int tipoDeDecodificacao = PainelEsquerdo.cmbListaDeCodificacao.getSelectedIndex();

    //imprime todo o passo a passo na tela
    switch(tipoDeDecodificacao) {
      case 0: //decodificacao Binaria
        fluxoBrutoDeBits =
        CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoBinaria(fluxoBrutoDeBitsPontoB);

        TelaPrincipal.imprimirNaTela(
          Conversao.asciiParaString(
            fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
      case 1: //decodificacoa Manchester
        fluxoBrutoDeBits =
        CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoManchester(fluxoBrutoDeBitsPontoB);

        TelaPrincipal.imprimirNaTela(
          Conversao.asciiParaString(
            fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
      case 2: //decodificacao Manchester Diferencial
        fluxoBrutoDeBits =
        CamadaFisicaReceptora.camadaFisicaReceptoraDecodificacaoManchesterDiferencial(fluxoBrutoDeBitsPontoB);

        TelaPrincipal.imprimirNaTela(
          Conversao.asciiParaString(
            fluxoBrutoDeBits, TelaPrincipal.ASCII_DECODIFICADO), TelaPrincipal.ASCII_DECODIFICADO);
        break;
    }

    CamadaDeAplicacaoReceptora.camadaDeAplicacaoReceptora(fluxoBrutoDeBits);
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoBinaria*
  Funcao: Decodificar os bits da codificacao binaria*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoBinaria(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);

    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_DECODIFICADO);

    return Conversao.bitsParaAscii(fluxoBrutoDeBits);
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoManchester*
  Funcao: Decodificar os bits da codificacao manchester*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoManchester(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);

    int novoTamanho = 0;
    int numeroDeBits =
    Integer.SIZE-Integer.numberOfLeadingZeros(fluxoBrutoDeBits[fluxoBrutoDeBits.length-1]);

    //calcula novo tamanho do vetor quadro
    if(numeroDeBits <= 16){
      novoTamanho = (fluxoBrutoDeBits.length*2)-1;
    }else{
      novoTamanho = fluxoBrutoDeBits.length*2;
    }

    int[] bitsDecodificados = new int[novoTamanho];
    int displayMask = 1 << 31;
    int valor = 0;

    for(int i=0, pos=0; i<fluxoBrutoDeBits.length; i++){
      int numero = fluxoBrutoDeBits[i];
      numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);

      //arredondando o numero de bits
      if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else{
        numeroDeBits = 32;
      }

      numero <<= 32-numeroDeBits; //posiciona os bits mais a esquerda

      for(int j=1; j<=numeroDeBits/2; j++){
        if((numero & displayMask) == 0){ //representa o bit 0
          valor <<= 1;
          valor = valor | 0;
        }else{ //representa o bit 1
          valor <<= 1;
          valor = valor | 1;
        }
        numero <<= 2;

        if(j%8 == 0){ //a cada 8 bits
          bitsDecodificados[pos] = valor;
          valor = 0; //reseta o inteiro
          pos++;
        }
      }
    }

    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(bitsDecodificados), TelaPrincipal.BIT_DECODIFICADO);

    return bitsDecodificados;
  }

  /* **************************************************************
  Metodo: camadaFisicaReceptoraDecodificacaoManchesterDiferencial*
  Funcao: Decodificar os bits da codificacao manchester diferencial*
  Parametros: int[] fluxoBrutoDeBits: bits a serem decodificados*
  Retorno: int[] bitsDecodificados*
  *************************************************************** */
  private static int[] camadaFisicaReceptoraDecodificacaoManchesterDiferencial(int[] fluxoBrutoDeBits) {
    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_RECEBIDO);

    int novoTamanho = 0;
    int numeroDeBits =
    Integer.SIZE-Integer.numberOfLeadingZeros(fluxoBrutoDeBits[fluxoBrutoDeBits.length-1]);

    //calcula novo tamanho do vetor fluxoBrutoDeBits
    if(numeroDeBits <= 16){
      novoTamanho = (fluxoBrutoDeBits.length*2)-1;
    }else{
      novoTamanho = fluxoBrutoDeBits.length*2;
    }

    int[] bitsDecodificados = new int[novoTamanho];
    int displayMask = 1 << 31;
    int valor = 0;
    boolean transicao = false;

    for(int i=0, pos=0; i<fluxoBrutoDeBits.length; i++){
      int numero = fluxoBrutoDeBits[i];
      numeroDeBits = Integer.SIZE-Integer.numberOfLeadingZeros(numero);

      //arredondando o numero de bits
      if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else{
        numeroDeBits = 32;
      }

      numero <<= 32-numeroDeBits; //posiciona os bits mais a esquerda

      for(int j=1; j<=numeroDeBits/2; j++){
        if((numero & displayMask) == 0){
          if(transicao){
            valor <<= 1;
            valor = valor | 1;

            transicao = !transicao; //reseta a transicao
          }else{
            valor <<= 1;
            valor = valor | 0;
          }
        }else{
          transicao = !transicao; //houve transicao

          if(transicao){
            valor <<= 1;
            valor = valor | 1;
          }else{
            valor <<= 1;
            valor = valor | 0;

            transicao = !transicao; //reseta a transicao
          }
        }
        numero <<= 2;

        if(j%8 == 0){ //a cada 8 bits
          bitsDecodificados[pos] = valor;
          valor = 0; //reseta o inteiro
          pos++;
        }
      }
    }

    TelaPrincipal.imprimirNaTela(
      Conversao.bitsParaString(bitsDecodificados), TelaPrincipal.BIT_DECODIFICADO);

    return bitsDecodificados;
  }
}