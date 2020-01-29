/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 29/01/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package camadas;

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
    int tipoDeCodificacao = TelaPrincipal.cmbListaDeCodificacao.getSelectedIndex();

    //imprime todo o passo a passo na tela
    switch(tipoDeCodificacao) {
      case 0: //codificao binaria
        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoBinaria(quadro);

        CamadaFisicaTransmissora.mostrarCodigoAscii(quadro, TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_CODIFICADO);
        break;
      case 1: //codificacao manchester
        fluxoBrutoDeBits = CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchester(quadro);

        CamadaFisicaTransmissora.mostrarCodigoAscii(quadro, TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_CODIFICADO);
        break;
      case 2: //codificacao manchester diferencial
        fluxoBrutoDeBits =  CamadaFisicaTransmissora.camadaFisicaTransmissoraCodificacaoManchesterDiferencial(quadro);

        CamadaFisicaTransmissora.mostrarCodigoAscii(quadro, TelaPrincipal.ASCII);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(Conversao.asciiParaBits(quadro)), TelaPrincipal.BIT_BRUTO);
        TelaPrincipal.imprimirNaTela(Conversao.bitsParaString(fluxoBrutoDeBits), TelaPrincipal.BIT_CODIFICADO);
        break;
    }

    MeioDeComunicacao.meioDeComunicacao(fluxoBrutoDeBits);
  }

   /* **************************************************************
  Metodo: camadaFisicaTransmissoraCodificacaoBinaria*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar os bits na
                codificacao binaria*
  Parametros: int[] quadro: vetor com os numeros em ASCII*
  Retorno: int[] bitsCodificados*
  *************************************************************** */
  private static int[] camadaFisicaTransmissoraCodificacaoBinaria(int[] quadro) {
    return Conversao.asciiParaBits(quadro);
  }

   /* **************************************************************
  Metodo: camadaFisicaTransmissoraCodificacaoManchester*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar os bits na
                codificacao manchester*
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

    return bitsCodificados;
  }

   /* **************************************************************
  Metodo: mostrarCodigoAscii*
  Funcao: Transformar os elementos do vetor quadro em bits e colocar os bits em
                uma string para imprimir na tela*
  Parametros: int[] quadro: vetor com os numeros em ASCII
                      int tipoDeImpressao: em qual caixa de texto sera impresso*
  Retorno: void*
  *************************************************************** */
  public static void mostrarCodigoAscii(int[] quadro, int tipoDeImpressao) {
    StringBuilder strAscii = new StringBuilder();

    if(tipoDeImpressao == 0){ //tipoDeImpressao == ASCII
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]);
        }
        else{
          strAscii.append(Character.toString((char)quadro[i])+"->"+quadro[i]+" ");
        }
      }
    }
    else{ //tipoDeImpressao == ASCII_CODIFICADO
      for(int i=0; i<quadro.length; i++){
        if(i == quadro.length-1){
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i]));
        }
        else{
          strAscii.append(quadro[i]+"->"+Character.toString((char)quadro[i])+" ");
        }
      }
    }

    TelaPrincipal.imprimirNaTela(strAscii.toString(), tipoDeImpressao);
  }
}