/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas;

import view.PainelEsquerdo;
import view.TelaPrincipal;
import util.Conversao;
import util.MeioDeComunicacao;

public class CamadaFisicaTransmissora {
  private static int[] fluxoBrutoDeBits;

  /* *****************************************************************************
  Metodo: camadaFisicaTransmissora*
  Funcao: Enviar os bits da mensagem para o meio de comunicacao, escolhendo qual a codificao utilizada*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: void*
  ***************************************************************************** */
  public static void camadaFisicaTransmissora(int[] quadro) {
    int tipoDeCodificacao = PainelEsquerdo.cmbListaDeCodificacao.getSelectedIndex();

    //imprime todo o passo a passo na tela
    switch(tipoDeCodificacao) {
      case 0: //codificao binaria
        //TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        //TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoBinaria(quadro);
        break;
      case 1: //codificacao manchester
        //TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        //TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;
      case 2: //codificacao manchester diferencial
        //TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        //TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits =  CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    }

    //MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits);
  }

  /* **************************************************************
  Metodo: camadaFisicaTransmissoraCodificacaoBinaria*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar
          os bits na codificacao binaria*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: int[] bitsCodificados*
  *************************************************************** */
  private static int[] camadaFisicaTransmissoraCodificacaoBinaria(int[] quadro) {
    int[] bitsCodificados = Conversao.asciiParaBits(quadro);

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsCodificados), TelaPrincipal.BIT_CODIFICADO);

    return bitsCodificados;
  }

  /* **************************************************************
  Metodo: camadaFisicaTransmissoraCodificacaoManchester*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar
          os bits na codificacao manchester*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: int[] bitsCodificados*
  *************************************************************** */
  private static int[] camadaFisicaTransmissoraCodificacaoManchester(int[] quadro) {
    int[] bitsBrutos = Conversao.asciiParaBits(quadro);

    //calcula o tamanho do vetor bitsCodificados
    int novoTamanho = 0;
    if(Integer.toBinaryString(bitsBrutos[bitsBrutos.length-1]).length() <= 16){
      novoTamanho = (bitsBrutos.length*2)-1;
    }else{
      novoTamanho = bitsBrutos.length*2;
    }

    int[] bitsCodificados = new int[novoTamanho];

    // cria um valor inteiro com 1 no bit mais à esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000

    //int com todos os bits 0s
    int valor = 0;//00000000 00000000 00000000 00000000

    for(int i=0, pos=0; i<bitsBrutos.length; i++){
      //pega o numero de bits do inteiro
      int numeroDeBits = Integer.toBinaryString(bitsBrutos[i]).length();

      //arredondando o numero de bits
      if(numeroDeBits <= 8){
        numeroDeBits = 8;
      }else if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else if(numeroDeBits <= 24){
        numeroDeBits = 24;
      }else if(numeroDeBits <= 32){
        numeroDeBits = 32;
      }

      int numero = bitsBrutos[i];
      numero <<= 32-numeroDeBits; //desloca para os primeiros 8 bits

      for(int j=1; j<=numeroDeBits; j++){
        if((numero & displayMask) == 0){
          valor <<= 1;
          valor = valor | 1;
          valor <<= 1;
          valor = valor | 0;
        }else{
          valor <<= 2;
          valor = valor | 1;
        }
        numero <<= 1;

        if(j == 16 || j == numeroDeBits){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }
      }
    }

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsCodificados), TelaPrincipal.BIT_CODIFICADO);

    return bitsCodificados;
  }

   /* **************************************************************
  Metodo: camadaFisicaTransmissoraCodificacaoManchesterDiferencial*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar os bits na
                codificacao manchester difenrencial*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: int[] bitsCodificados*
  *************************************************************** */
  private static int[] camadaFisicaTransmissoraCodificacaoManchesterDiferencial(int[] quadro) {
    int[] bitsBrutos = Conversao.asciiParaBits(quadro);

    //calcula o tamanho do vetor bitsCodificados
    int novoTamanho = 0;
    if(Integer.toBinaryString(bitsBrutos[bitsBrutos.length-1]).length() <= 16){
      novoTamanho = (bitsBrutos.length*2)-1;
    }else{
      novoTamanho = bitsBrutos.length*2;
    }

    int[] bitsCodificados = new int[novoTamanho];

    // cria um valor inteiro com 1 no bit mais à esquerda e 0s em outros locais
    int displayMask = 1 << 31;//10000000 00000000 00000000 00000000

    //int com todos os bits 0s
    int valor = 0;//00000000 00000000 00000000 00000000

    boolean sinal = false; //sinal para indentificar transicao

    for(int i=0, pos=0; i<bitsBrutos.length; i++){
      //pega o numero de bits do inteiro
      int numeroDeBits = Integer.toBinaryString(bitsBrutos[i]).length();

      //arredondando o numero de bits
      if(numeroDeBits <= 8){
        numeroDeBits = 8;
      }else if(numeroDeBits <= 16){
        numeroDeBits = 16;
      }else if(numeroDeBits <= 24){
        numeroDeBits = 24;
      }else if(numeroDeBits <= 32){
        numeroDeBits = 32;
      }

      int numero = bitsBrutos[i];
      numero <<= 32-numeroDeBits; //desloca para os primeiros 8 bits

      for(int j=1; j<=numeroDeBits; j++){
        if((numero & displayMask) == 0){
          if(sinal){
            valor <<= 1;
            valor = valor | 0;
            valor <<= 1;
            valor = valor | 1;
          }else{
            valor <<= 1;
            valor = valor | 1;
            valor <<= 1;
            valor = valor | 0;
          }
        }else{
          sinal = !sinal; //houve transicao

          if(sinal){
            valor <<= 1;
            valor = valor | 0;
            valor <<= 1;
            valor = valor | 1;
          }else{
            valor <<= 1;
            valor = valor | 1;
            valor <<= 1;
            valor = valor | 0;
          }
        }
        numero <<= 1;

        if(j == 16 || j == numeroDeBits){
          bitsCodificados[pos] = valor;
          valor = 0;
          pos++;
        }
      }
    }

    for(int i=0; i<bitsCodificados.length; i++){
      System.out.println(bitsCodificados[i]);
    }

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsCodificados), TelaPrincipal.BIT_CODIFICADO);

    return bitsCodificados;
  }
}