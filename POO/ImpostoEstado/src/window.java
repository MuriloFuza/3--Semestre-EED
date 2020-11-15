
/***************************************************************
* Students: Murilo Fuza da Cunha & Ivo Henrique 
* Course: Bachelor of Computer Science
*
* POO Project - Tax collection
*
* Compiler: openjdk 14.0.2 2020-07-14
* Operacional System: Manjaro - Kernel: Linux 5.4.74-1-MANJARO
*
* Use the code at will, we just ask you to reference us the creators in their 
* academic work
***************************************************************/
//commit
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;

import javax.swing.JDesktopPane;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import components.*;
import interfaces.Estado;
import classes.*;

public class window extends JFrame{
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private JMenuBar barramenu;
  private JMenu inserir, relatorio, limpar, sair;
  private JMenuItem insFisica,insJurica,insIPVA, sairP, cancelar, relatorioImp, 
  limparAll;
  private JDesktopPane aJanela;
  private ArrayList<Estado> lista = new ArrayList<Estado>();
  private JPanel fisica, juridica, ipva, painelf, painelj, paineli;
  public Field nome, rendaBrutaAnual, cpf, gastoS, gastoE, cgc, gastoP, 
  gastoEq, placa, marca;
  public JButton btInserirF, btInserirJ, btInserirIpva;
  public int guarda;//1-fisica,2-juridica,3-ipva

  public window(){
    super("Calculadora de impostos");
    setLayout(new BorderLayout());
    aJanela = new JDesktopPane();
    aJanela.setLayout(new BorderLayout());

    barramenu = new JMenuBar();
    setJMenuBar(barramenu);

    inserir = new JMenu("Inserir");
    relatorio = new JMenu("Relatorio");
    limpar = new JMenu("Limpar");
    sair = new JMenu("Sair");

    insFisica = new JMenuItem("Inserir P.Fisica");
    insJurica = new JMenuItem("Inserir P.Juridica");
    insIPVA = new JMenuItem("Inserir IPVA");
    inserir.add(insFisica);
    inserir.add(insJurica);
    inserir.add(insIPVA);

    limparAll = new JMenuItem("Limpar campos");
    limpar.add(limparAll);

    sairP = new JMenuItem("Sair");
    cancelar = new JMenuItem("Cancelar");
    sair.add(sairP);
    sair.add(cancelar);

    relatorioImp = new JMenuItem("Gerar Relatorio");
    relatorio.add(relatorioImp);

    barramenu.add(inserir);
    barramenu.add(relatorio);
    barramenu.add(limpar);
    barramenu.add(sair);

    nome = new Field("Nome:",20);
    rendaBrutaAnual = new Field("Renda Bruta Anual:",10);

    eventosCadastro funcC = new eventosCadastro();
    insFisica.addActionListener(funcC);
    insJurica.addActionListener(funcC);
    insIPVA.addActionListener(funcC);

    eventosSair funcS = new eventosSair();
    sairP.addActionListener(funcS);

    eventosRelat funcR = new eventosRelat();
    relatorioImp.addActionListener(funcR);

    eventosLimpar funcL = new eventosLimpar();
    limparAll.addActionListener(funcL);
  }
  
  private class eventosLimpar implements ActionListener{

    public void actionPerformed(ActionEvent event){
      if(event.getSource()==limparAll){
        if(guarda==1){
          nome.resetValue();
          rendaBrutaAnual.resetValue();
          cpf.resetValue();
          gastoS.resetValue(); 
          gastoE.resetValue();  
        }
        if(guarda==2){
          nome.resetValue();
          rendaBrutaAnual.resetValue();
          cgc.resetValue(); 
          gastoP.resetValue(); 
          gastoEq.resetValue(); 
        }
        if(guarda==3){
          placa.resetValue(); 
          marca.resetValue(); 
        }
      }
    }
  }

  private class eventosCadastro implements ActionListener{

    public void actionPerformed(ActionEvent event) {
      getContentPane().removeAll();
      if(event.getSource()==insFisica){
        guarda = 1;
        fisica = new criaPainelF();
        getContentPane().add(fisica);

      }
      else if(event.getSource()==insJurica){
        guarda = 2;
        juridica = new criaPainelJ();
        getContentPane().add(juridica);
      }
      else if(event.getSource()==insIPVA){
        guarda = 3;
        ipva = new criaPainelIpva();
        getContentPane().add(ipva);
      }
      pack();
      revalidate();
      repaint();
    }
  }

  private class eventosSair implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent event) {
      if(event.getSource()==sairP){
        System.exit(0);
      }
      else if(event.getSource()==cancelar){
      }
    }
  }

  private class eventosRelat implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent event) {
      String saida = "";
      String titulo = "Relatorio Anual de Impostos";
      if(event.getSource()==relatorioImp){
        if(lista.isEmpty()){
          saida = "Lista Vazia";
      }else{
        for(int i =0; i < lista.size();i++){
          if(lista.get(i) instanceof Fisica){
            saida += "\n\nPESSOA FISICA\n\n" + lista.get(i).toString();
          }
        }
        for(int i =0; i < lista.size();i++){
          if(lista.get(i) instanceof Juridica){
            saida += "\n\nPESSOA JURIDICA\n\n" + lista.get(i).toString();
          }
        }
        for(int i =0; i < lista.size();i++){
          if(lista.get(i) instanceof ipva){
            saida += "\n\nIPVA\n\n" + lista.get(i).toString();
          }
        }
      }
      JTextArea textArea = new JTextArea(saida);
                textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);  
                scrollPane.setPreferredSize( new Dimension(300,180));
                JOptionPane.showMessageDialog(null, scrollPane, titulo,
                JOptionPane.PLAIN_MESSAGE);
      
      }
    }
  }

  private class criaPainelF extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public criaPainelF() {
      painelf = new JPanel();
      painelf.setLayout(new BoxLayout(painelf, BoxLayout.Y_AXIS));
      painelf.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      cpf = new Field("CPF: ",14);
      gastoS = new Field("Gastos com Saude:",10);
      gastoE = new Field("Gastos com Educacao:",10);
      painelf.add(nome);
      painelf.add(rendaBrutaAnual);
      painelf.add(cpf);
      painelf.add(gastoS);
      painelf.add(gastoE);
      btInserirF = new JButton("Inserir");
      painelf.add(btInserirF);
      add(painelf, BorderLayout.CENTER);

      btInserirF.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          String cpfF, gastoSF, gastoEF, saida, titulo, nomeF, rendaBA;
          Double gastoSaude = 0.0, gastoEducacao = 0.0, rendaBrutaAual = 0.0;
          cpfF = cpf.getValue();
          gastoSF = gastoS.getValue();
          gastoEF = gastoE.getValue();
          rendaBA = rendaBrutaAnual.getValue();
          nomeF = nome.getValue();
          
          if(nome.getValue().equals("")| rendaBrutaAnual.getValue().equals("") |
          cpf.getValue().equals("") | gastoS.getValue().equals("") |
          gastoE.getValue().equals("")){
            titulo = "ERRO";
            saida = "ERRO - Preencha todos os campos!";
            JOptionPane.showMessageDialog(null, saida, titulo,
            JOptionPane.PLAIN_MESSAGE);
          }else{
            try {
              gastoSaude = Double.parseDouble(gastoSF); 
            } catch (Exception e) {
              gastoS.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no seu gasto com saude!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }
  
            try {
              gastoEducacao = Double.parseDouble(gastoEF); 
            } catch (Exception e) {
              gastoE.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no seu gasto com educacao!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }
            try {
              rendaBrutaAual = Double.parseDouble(rendaBA); 
            } catch (Exception e) {
              rendaBrutaAnual.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no sua Renda bruta anual!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }

            Fisica f = new Fisica(nomeF,rendaBrutaAual,cpfF,gastoSaude,
            gastoEducacao);
            lista.add(f);
            JOptionPane.showMessageDialog(null, "Pessoa Fisica Inserida com"+ 
            "sucesso");

            nome.resetValue();rendaBrutaAnual.resetValue();cpf.resetValue();
            gastoS.resetValue();gastoE.resetValue(); 
          }    
        }
      });
    }
  }

  private class criaPainelJ extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public criaPainelJ() {
      painelj = new JPanel();
      painelj.setLayout(new BoxLayout(painelj, BoxLayout.Y_AXIS));
      painelj.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      cgc = new Field("CGC: ",14);
      gastoP = new Field("Gastos Pessoais: ",10);
      gastoEq = new Field("Gastos com Equipamento: ",10);
      painelj.add(nome);
      painelj.add(rendaBrutaAnual);
      painelj.add(cgc);
      painelj.add(gastoP);
      painelj.add(gastoEq);
      btInserirJ = new JButton("Inserir");
      painelj.add(btInserirJ);
      add(painelj, BorderLayout.CENTER);

      btInserirJ.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent event){
          String cgcJ, gastoPJ, gastoEqJ, saida, titulo, nomeJ, rendaBA;
          Double gastoPessoal = 0.0, gastoEquipamento = 0.0, rendaBrutaAual=0.0;
          cgcJ = cgc.getValue();
          gastoPJ = gastoP.getValue();
          gastoEqJ = gastoEq.getValue();
          rendaBA = rendaBrutaAnual.getValue();
          nomeJ = nome.getValue();

          if(nome.getValue().equals("")| rendaBrutaAnual.getValue().equals("") |
          cgc.getValue().equals("") | gastoP.getValue().equals("") |
          gastoEq.getValue().equals("")){
            titulo = "ERRO";
            saida = "ERRO - Preencha todos os campos!";
            JOptionPane.showMessageDialog(null, saida, titulo,
            JOptionPane.PLAIN_MESSAGE);
          }else{
            try {
              gastoPessoal = Double.parseDouble(gastoPJ); 
            } catch (Exception e) {
              gastoP.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no seu gasto com saude!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }
  
            try {
              gastoEquipamento = Double.parseDouble(gastoEqJ); 
            } catch (Exception e) {
              gastoEq.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no seu gasto com educacao!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }
            try {
              rendaBrutaAual = Double.parseDouble(rendaBA); 
            } catch (Exception e) {
              rendaBrutaAnual.resetValue();
              titulo = "ERRO";
              saida = "ERRO - Preencha corretamente \no sua Renda bruta anual!";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }

            Juridica j = new Juridica(nomeJ,rendaBrutaAual,cgcJ,gastoPessoal,
            gastoEquipamento);
            lista.add(j);
            JOptionPane.showMessageDialog(null, "Pessoa Juridica Inserida com"+
            "sucesso");
          }
        }
      });
    }
  }

  private class criaPainelIpva extends JPanel{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public criaPainelIpva() {
      paineli = new JPanel();
      paineli.setLayout(new BoxLayout(paineli, BoxLayout.Y_AXIS));
      paineli.setBorder(javax.swing.BorderFactory.createEtchedBorder());
      placa = new Field("Placa: ",7);
      marca= new Field("Marca: ",1);
      paineli.add(placa);
      paineli.add(marca);
      btInserirIpva = new JButton("Inserir");
      paineli.add(btInserirIpva);
      add(paineli, BorderLayout.CENTER);

      btInserirIpva.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent Event){
          String placaC, marcaC, saida, titulo;
          Double valorAnual = 0.0;
          if(placa.getValue().equals("") | marca.getValue().equals("")){
            titulo = "ERRO";
            saida = "ERRO - Preencha todos os campos!";
            JOptionPane.showMessageDialog(null, saida, titulo,
            JOptionPane.PLAIN_MESSAGE);
          }else{
            if(marca.getValue().length() > 1){
              titulo = "ERRO";
              saida = "ERRO - Insira uma marca existente!\n "+
              "W - Volks | G - Gm | F - Fiat | O - Outros";
              JOptionPane.showMessageDialog(null, saida, titulo,
              JOptionPane.PLAIN_MESSAGE);
            }else{
              marcaC  = marca.getValue();
              marcaC.toUpperCase();
              placaC = placa.getValue();
              if(marcaC.equals("W"))
                valorAnual = 1000.00;
              if(marcaC.equals("G"))
                valorAnual = 1200.00;
              if(marcaC.equals("F"))
                valorAnual = 900.00;
              if(marcaC.equals("O"))
                valorAnual = 1500.00;
              ipva i = new ipva(placaC,marcaC,valorAnual);
              lista.add(i);
              JOptionPane.showMessageDialog(null,"IPVA inserido com sucesso");
            }
          }
        }
      });
    }
  }

  public static void main(String[] args){
    window f = new window();
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.pack();
      f.setLocationRelativeTo(null);
      f.setVisible(true);
  }
}
