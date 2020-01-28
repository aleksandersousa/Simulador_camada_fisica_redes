import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Canvas extends JPanel{
  private final int ESPACO;

  private static int[] fluxoDeBits;
  private static boolean flag;

  private int x;
  private int y;
  private int x2;
  private int y2;
  private int espacamento;

  public Canvas() {
    this.initGUIComponents();

    this.y = this.getHeight()/2;
    this.x2 = 20;
    this.y2 = y;
    this.ESPACO = 50;
    this.espacamento = 40;
  }

  private void initGUIComponents() {
    this.setBounds(10, 250, 905,  100);
    this.setBackground(Color.gray);
  }

  private void criarLinhaBit0(Graphics g, int a, boolean flag) {
    x2 = a;
    y2 = y = 10;

    g.drawLine(x, y, x2, y2);
    x = x2;
    y2 = 80;
    g.drawLine(x, y, x2, y2);
    y = y2;
    x2 += 20;
    g.drawLine(x, y, x2, y2);

    if(flag){
      x = x2;
      y2 = 10;
      g.drawLine(x, y, x2, y2);

    }else{
      x = x2;
    }
  }

  private void criarLinhaBit1(Graphics g, int a, boolean flag) {
    x2 = a;
    y2 = y = 80;

    g.drawLine(x, y, x2, y2);
    x = x2;
    y2 = 10;
    g.drawLine(x, y, x2, y2);
    y = y2;
    x2 += 30;
    g.drawLine(x, y, x2, y2);

    if(flag){
      x = x2;
      y2 = 80;
      g.drawLine(x, y, x2, y2);

    }else{
      x = x2;
    }
  }

  private void criarLinhaTracejada(Graphics g) {
    Graphics2D g2D = (Graphics2D) g.create();

    Stroke linhaTracejada =
    new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);

    g2D.setStroke(linhaTracejada);
    g2D.drawLine(0, this.getHeight()/2, this.getWidth(), this.getHeight()/2);
    g2D.dispose();
  }

  private void criarLinhaInicial(Graphics g) {
    if(fluxoDeBits[0] == 0){
      g.drawLine(x, y, x2, y2);
      x = x2;
      y = 10;
      g.drawLine(x, y, x2, y2);

    } else{
      g.drawLine(x, y, x2, y2);
      x = x2;
      y = 80;
      g.drawLine(x, y, x2, y2);
    }
  }

  private void criarLinhaFinal(Graphics g, int tipoDeBit, int espacamento) {
    if(tipoDeBit == 0){
      this.criarLinhaBit0(g, espacamento, false);
      x = x2;
      y = 40;
      g.drawLine(x, y, x2, y2);
      x2 += 20;
      y2 = y;
      g.drawLine(x, y, x2, y2);

    }else{
      this.criarLinhaBit1(g, espacamento, false);
      x = x2;
      y = this.getHeight()/2;
      g.drawLine(x, y, x2, y2);
      x2 += 20;
      y2 = y;
      g.drawLine(x, y, x2, y2);
    }
  }

  private void resetarPontos(){
    x = 0;
    x2 = 20;
    y = this.getHeight()/2;
    y2 = y;
    espacamento = 40;
  }

  public static void setFluxoDeBits(int[] fluxoDeBits) {
    Canvas.fluxoDeBits = fluxoDeBits;
  }

  public static void setFlag(boolean flag) {
    Canvas.flag = flag;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    this.criarLinhaTracejada(g);
    if(flag){
      this.criarLinhaInicial(g);

      for(int i=0; i<fluxoDeBits.length-1; i++){
        if(fluxoDeBits[i] == 0){
          if(fluxoDeBits[i+1] == 1){
            this.criarLinhaBit0(g, espacamento, false);
            espacamento += ESPACO;

          }else{
            this.criarLinhaBit0(g, espacamento, true);
            espacamento += ESPACO;
          }

        }else{
          if(fluxoDeBits[i+1] == 0){
            this.criarLinhaBit1(g, espacamento, false);
            espacamento += ESPACO;

          }else{
            this.criarLinhaBit1(g, espacamento, true);
            espacamento += ESPACO;
          }
        }
      }
      this.criarLinhaFinal(g, Canvas.fluxoDeBits[Canvas.fluxoDeBits.length-1], espacamento);
      this.resetarPontos();
    }
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(18442,  0);
  }
}