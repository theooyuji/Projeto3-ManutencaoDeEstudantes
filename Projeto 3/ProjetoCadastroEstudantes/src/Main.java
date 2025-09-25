import java.io.*;
import java.util.Scanner;

public class Main
{
    static ManterEstudantes estud;
    static Scanner teclado;
    static String[] Siglas = new String[15];

    public static void main(String[] args) throws Exception {
        estud = new ManterEstudantes(15);
        teclado = new Scanner(System.in);   // System.in representa o teclado
        estud.lerDados("Projeto 3/ProjetoCadastroEstudantes/estudantes.txt");
        lerCurso("C:\\Users\\u25155\\IdeaProjects\\Projeto3-ManutencaoDeEstudantes\\Projeto 3\\ProjetoCadastroEstudantes\\siglasDisc.txt");
        seletorDeOpcoes();
        estud.gravarDados("Projeto 3/ProjetoCadastroEstudantes/estudantes.txt");
    }

    public static void seletorDeOpcoes() throws Exception {
        int opcao = -1;
        do {
            System.out.println("\nOperações disponíveis:");
            System.out.println("0 - Terminar programa");
            System.out.println("1 - Incluir estudantes");
            System.out.println("2 - Excluir estudantes");
            System.out.println("3 - Exibir estudantes");
            System.out.println("4 - Listar estudantes");
            System.out.println("5 - Alterar estudantes");
            System.out.println("6 - Ir ao início");
            System.out.println("7 - Ir ao proximo");
            System.out.println("8 - Ir ao anterior");
            System.out.println("9 - Ir ao último");
            System.out.println("10 - Estatísticas");
            System.out.println("\nDigite o número da operação desejada:");
            opcao = teclado.nextInt();  // lê um inteiro pelo teclado

            // definir e tratar a operação escolhida pelo usuário:
            switch (opcao)
            {
                case 1 : inclusao(); break;
                case 2 : exclusao(); break;
                case 3 : exibicao(); break;
                case 4 : listagem();
            }
        }
        while (opcao != 0);
    }

    public static void inclusao() throws Exception {
        String curso, ra = "99999", nome;
        while (!ra.equals("00000"))
        {
            System.out.print("RA do estudante: (0) para terminar: ");
            int raDigitado = teclado.nextInt();
            if (raDigitado != 0)
            {
                ra = String.format("%05d", raDigitado);
                // não podemos ter ra repetido, temos de pesquisá-lo no vetor
                // antes de prosseguir a inclusão:
                // criamos um objeto Estudante para poder chamar o método existe()
                Estudante proc = new Estudante(ra);
                if (estud.existe(proc))     // ajusta valor de ondeEsta
                    System.out.println("\nRA repetido!");
                else  // ra não repetido, lemos os demais dados
                {
                    System.out.print("Código do curso: ");
                    int cursoLido = teclado.nextInt();
                    curso = String.format("%02d", cursoLido); // "00" a "99"
                    System.out.print("Nome do estudante: ");
                    nome = teclado.next();
                    // inclui em ordem crescente de ra, usando o valor de ondeEsta
                    // como índice de inclusão do novo estudante
                    estud.incluirEm(new Estudante(curso, ra, nome), estud.getOnde());
                    System.out.println("\nEstudante incluído.");
                }
            }
            else
                ra = String.format("%05d", raDigitado);
        }
    }

    public static void exclusao() throws Exception
    {
        String ra = "99999";
        while (!ra.equals("00000")) {
            System.out.print("RA do estudante: (0) para terminar: ");
            int raDigitado = teclado.nextInt();
            if (raDigitado != 0) {
                ra = String.format("%05d", raDigitado);
                // temos de pesquisar esse ra no vetor para descobrirmos
                // em que índice ele está para podermos excluir desse índice
                // criamos um objeto Estudante para poder chamar o método existe()
                Estudante proc = new Estudante(ra);
                if (!estud.existe(proc))     // ajusta valor de ondeEsta
                    System.out.println("\nRA não encontrado!");
                else  // ra não repetido, lemos os demais dados
                {
                    estud.excluir(estud.getOnde());
                    System.out.println("Estudante excluído.");
                }
            }
            else
                ra = String.format("%05d", raDigitado);
        }
    }

    public static void exibicao() throws Exception
    {
        String ra = "99999";
        while (!ra.equals("00000")) {
            System.out.print("RA do estudante: (0) para terminar: ");
            int raDigitado = teclado.nextInt();
            if (raDigitado != 0) {
                ra = String.format("%05d", raDigitado);
                // temos de pesquisar esse ra no vetor para descobrirmos
                // em que índice ele está para podermos excluir desse índice
                // criamos um objeto Estudante para poder chamar o método existe()
                Estudante proc = new Estudante(ra);
                String cursos = "                                                         ";
                if (!estud.existe(proc))     // ajusta valor de ondeEsta
                    System.out.println("\nRA não encontrado!");
                else  // ra não repetido, lemos os demais dados
                {
                    int quantasNotas = estud.valorDe(estud.getOnde()).getQuantasNotas();
                    for(int i = 0 ; i < quantasNotas; i++){
                        cursos += Siglas[i];
                        cursos += " ";
                    }
                    System.out.println(cursos);
                    System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
                    System.out.println(estud.valorDe(estud.getOnde()));
                }
            }
            else
                ra = String.format("%05d", raDigitado);
        }
    }

    public static void listagem() throws Exception
    {
        System.out.println("\nRelação de estudantes cadastrados:");
        String cursos = "                                                         ";
        for(int i = 0 ; i < 15; i++){
            cursos += Siglas[i];
            cursos += " ";
        }
        System.out.println(cursos);
        System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
        for (int indice=0; indice < estud.getTamanho(); indice++)
            System.out.println(estud.valorDe(indice));
        System.out.println();
    }
    public static void lerCurso(String nomeDoArquivo) throws FileNotFoundException{
        BufferedReader leitor = null;
        boolean parar = false;
        try{
            leitor = new BufferedReader(new FileReader(nomeDoArquivo));

        }
        catch(FileNotFoundException erro){
            System.out.println("Não foi possível abrir o arquivo !");
            parar = true;
        }
        try{
            int inicioSigla = 0;
            int fimSigla = 6;
            int i = 0 ;
            while(!parar){
                String linha = leitor.readLine();
                if(!linha.isEmpty()){
                    String curso = linha.substring(inicioSigla,fimSigla);
                    Siglas[i] = curso;
                    i++;
                }
                else{
                    parar = true;
                }
            }
        }
        catch (Exception erro){
            System.out.println("Erro na leitura de dados!");
        }
    }
}