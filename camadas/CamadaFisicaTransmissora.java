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
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoBinaria(quadro);
        break;
      case 1: //codificacao manchester
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchester(quadro);
        break;
      case 2: //codificacao manchester diferencial
        TelaPrincipal.imprimirNaTela(Conversao.asciiParaString(quadro, TelaPrincipal.ASCII), TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);

        fluxoBrutoDeBits =  CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);
        break;
    }

    MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits);
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
    int[] bitsCodificados = new int[bitsBrutos.length*2];

    for(int i=0, j=0; i<bitsBrutos.length; i++){
      if(bitsBrutos[i] == 1){
        bitsCodificados[j] = 0;
        bitsCodificados[++j] = 1;
      }
      else{
        bitsCodificados[j] = 1;
        bitsCodificados[++j] = 0;
      }
      j++;
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
    int[] bitsCodificados = new int[bitsBrutos.length*2];

    for(int i=0, j=0; i<bitsBrutos.length; i++){
      if(i == 0){
        if(bitsBrutos[i] == 0){
          bitsCodificados[j] = 1;
          bitsCodificados[++j] = 0;
        }
        else{
          bitsCodificados[j] = 0;
          bitsCodificados[++j] = 1;
        }
      }
      else{
        if((bitsBrutos[i] == 1 && bitsBrutos[i-1] == 0) ||
        (bitsBrutos[i] == 1 && bitsBrutos[i-1] == 1))
        {
          bitsCodificados[j] = bitsCodificados[j-1];
          bitsCodificados[++j] = 1-bitsCodificados[j-1];
        }
        else if(
        (bitsBrutos[i] == 0 && bitsBrutos[i-1] == 1) ||
        (bitsBrutos[i] == 0 && bitsBrutos[i-1] == 0))
        {
          bitsCodificados[j] = 1-bitsCodificados[j-1];
          bitsCodificados[++j] = 1-bitsCodificados[j-1];
        }
      }
      j++;
    }

    TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(bitsCodificados), TelaPrincipal.BIT_CODIFICADO);

    return bitsCodificados;
  }
}