/* ***************************************************************
Autor: Aleksander Santos Sousa*
Matricula: 201810825*
Inicio: 23/01/2020*
Ultima alteracao: 01/02/2020*
Nome: Simulador de Redes*
Funcao: Simular o envio de uma mensagem de texto.
*************************************************************** */
package img;

import java.awt.Image;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

public class Images {
  private ArrayList<Image> linhas;

  /* ****************************************************************
  Metodo: Images*
  Funcao: Construtor da classe Images. Carrega as imagens*
  Parametros: nulo*
  Retorno: void*
  *************************************************************** */
  public Images() {
    try{
      this.linhas = new ArrayList<Image>();

      for(int i=0; i<=3; i++){
        InputStream input = Images.class.getResourceAsStream("linha"+i+".png");
        Image imagem = ImageIO.read(input);
        linhas.add(imagem);
      }
    }catch(IOException e){
      System.out.println("Erro na leitrua das imagens!");
    }
  }

  /* ****************************************************************
  Metodo: getLinhas*
  Funcao: retorna array de imagens*
  Parametros: ArrayList<Image> linhas = array de imagens*
  Retorno: Image*
  *************************************************************** */
  public ArrayList<Image> getLinhas() {
    return this.linhas;
  }
}