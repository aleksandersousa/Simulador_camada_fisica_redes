public class MeioDeComunicacao {

  /* *****************************************************************************
  Metodo: meioDeComunicacao*
  Funcao: Enviar os bits recebidos da camada fisica transmissora para a camada fisica receptora*
  Parametros: int[] fluxoBrutoDeBits: vetor com os os bits*
  Retorno: void*
  ***************************************************************************** */
  public static void meioDeComunicacao(int[] fluxoBrutoDeBits) {
    int[] fluxoBrutoDeBitsPontoA = fluxoBrutoDeBits;
    int[] fluxoBrutoDeBitsPontoB = new int[fluxoBrutoDeBitsPontoA.length];

    for(int i=0; i<fluxoBrutoDeBitsPontoA.length; i++){
      fluxoBrutoDeBitsPontoB[i] = fluxoBrutoDeBitsPontoA[i];
    }

    //Pintando painel com os bits
    Canvas.setFluxoDeBits(fluxoBrutoDeBitsPontoB);
    Canvas.setFlag(true);
    TelaPrincipal.repintarPainel();

    CamadaFisicaReceptora.camadaFisicaReceptora(fluxoBrutoDeBitsPontoB);
  }
}