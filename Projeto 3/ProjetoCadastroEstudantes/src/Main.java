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
                case 0 : break;
                case 1 : inclusao(); break;
                case 2 : exclusao(); break;
                case 3 : exibicao(); break;
                case 4 : listagem(); break;
                case 5 : alterar(); break;
                case 6 : irAoInicio();break;
                case 7 : irAoProximo(); break;
                case 8 : irAoAnterior();break;
                case 9 : irAoUltimo(); break;
                case 10: estastisticas(); break;
                default:
                        System.out.println("Digite um número de opção válido !");
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
                    teclado.nextLine();
                    nome = teclado.nextLine();
                    Estudante atual = new Estudante(curso, ra, nome);
                    System.out.print("Quantidade de notas desse aluno: ");
                    teclado.nextLine();
                    int qtasNotas = teclado.nextInt();
                    int contador = 0;
                    while(contador < qtasNotas){
                        System.out.println("Digite a " + (contador + 1) +"ª nota, que é da matéria " + Siglas[contador]);
                        double nota = teclado.nextDouble();
                        atual.incluirNota(nota);
                        contador++;
                        teclado.nextLine();
                    }

                    // inclui em ordem crescente de ra, usando o valor de ondeEsta
                    // como índice de inclusão do novo estudante
                    estud.incluirEm(atual, estud.getOnde());
                    System.out.println("\nEstudante incluído.");
                    teclado.nextLine();
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


    public static void alterar() throws Exception {
        String ra = "99999";
        while(!ra.equals("00000")){
            System.out.print("Digite o ra do aluno que será alterado (0 para sair): ");
            int raDigitado = teclado.nextInt();
            if(raDigitado != 0 ){
                ra = String.format("%05d", raDigitado);
                Estudante atual = estud.valorDe(estud.getOnde());
                if(!estud.existe(new Estudante(ra))){
                    System.out.println("Não existe aluno com esse ra !");
                    return;
                }
                String cursos = "                                                         ";
                int quantasNotas = estud.valorDe(estud.getOnde()).getQuantasNotas();
                for(int i = 0 ; i < quantasNotas; i++){
                    cursos += Siglas[i];
                    cursos += " ";
                }
                System.out.println(cursos);
                System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
                System.out.println(atual);
                System.out.print("Digite o código do curso (Pressione Enter para manter o curso atual): ");
                teclado.nextLine();
                String cursoDigitado = String.format("%2s", teclado.nextLine());
                if(cursoDigitado.isBlank()){
                    cursoDigitado = atual.getCurso();
                }
                System.out.print("Digite o nome do aluno (Pressione Enter para manter o nome atual): ");
                String nomeDigitado = String.format("%-30s", teclado.nextLine());
                if(nomeDigitado.isBlank()){
                    nomeDigitado = atual.getNome();
                }
                System.out.print("Digite a quantidade de notas do aluno (Pressione Enter para manter): ");
                double[] notas = atual.getNotas();
                int qtasNotas = teclado.nextInt();
                for(int i = 0 ; i < qtasNotas; i++){
                    System.out.println("Digite a "+(i+1) +"ª nota do aluno, que é da matéria " + Siglas[i]);
                    double nota;
                    String notaDigitada = String.format("%4s", teclado.nextLine());
                    if(notaDigitada.isBlank()){
                        nota = notas[i];
                    }
                    else{
                        nota = Double.parseDouble(notaDigitada);
                    }
                    notas[i] = nota;
                }
                Estudante proc = new Estudante(cursoDigitado,ra,nomeDigitado);
                proc.setQuantasNotas(notas.length);
                proc.setNotas(notas);
                estud.alterar(proc,estud.getOnde());
            }
            else{
                ra = String.format("%05d",raDigitado);
            }
        }
    }

    public static void irAoInicio(){
        estud.irAoInicio();
        String cursos = "                                                         ";
        for(int i = 0 ; i < 15; i++){
            cursos += Siglas[i];
            cursos += " ";
        }
        System.out.println(cursos);
        System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
        System.out.println(estud.valorDe(estud.getPosicaoAtual()));
    }

    public static void irAoProximo(){
        estud.irAoProximo();
        String cursos = "                                                         ";
        for(int i = 0 ; i < 15; i++){
            cursos += Siglas[i];
            cursos += " ";
        }
        System.out.println(cursos);
        System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
        System.out.println(estud.valorDe(estud.getPosicaoAtual()));
    }

    public static void irAoAnterior(){
        estud.irAoAnterior();
        String cursos = "                                                         ";
        for(int i = 0 ; i < 15; i++){
            cursos += Siglas[i];
            cursos += " ";
        }
        System.out.println(cursos);
        System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
        System.out.println(estud.valorDe(estud.getPosicaoAtual()));
    }

    public static void irAoUltimo(){
        estud.irAoFim();
        String cursos = "                                                         ";
        for(int i = 0 ; i < 15; i++){
            cursos += Siglas[i];
            cursos += " ";
        }
        System.out.println(cursos);
        System.out.println("\n"+"Curso  RA    Nome                           QtasNotas    Notas");
        System.out.println(estud.valorDe(estud.getPosicaoAtual()));
    }

    public static void estastisticas() throws Exception {

        Estudante maiorMedia = estudMaiorMedia();
        double maiorNota = maioresEMenoresNotas(maiorMedia)[1];
        double menorNota = maioresEMenoresNotas(maiorMedia)[0];
        System.out.println("-----Estastísticas-----");
        System.out.println("\nDisciplina com maior número de aprovados: " + discAprovados());
        System.out.println("Disciplina com maior número de retidos: " + discRetidos());
        System.out.println("Estudante com maior média: " + maiorMedia.getNome());
        System.out.println("Maior nota do estudante com maior média: " + maiorNota);
        System.out.println("Menor nota do estudante com maior média: " + menorNota);
        for(int i = 0 ; i < Siglas.length; i++){
            System.out.println("Média dos estudante na matéria " + Siglas[i]+": " + mediaCurso(Siglas[i]));
        }
        System.out.println("Aluno com maior nota na disciplina com menor média: " + maiorNotaMenorMedia().getNome() );
        System.out.println("Aluno com menor nota na disciplina com maior média: " + menorNotaMaiorMedia().getNome());
    }

    public static String discAprovados(){
        int contAprovadosAtual;
        int contAprovadosMax = 0;
        int indMax = 0;
        for(int i = 0; i < Siglas.length; i++){
            contAprovadosAtual = 0;
            for(int ii = 0 ; ii < estud.getTamanho(); ii++){
                Estudante atual = estud.valorDe(ii);
                double[] nota = atual.getNotas();
                if(nota[i] >= 5.0){
                    contAprovadosAtual ++;
                }
                if(contAprovadosAtual > contAprovadosMax){
                    contAprovadosMax = contAprovadosAtual;
                    indMax = i;
                }
            }
        }
        return Siglas[indMax];
    }

    public static String discRetidos(){
        int contRetidosAtual;
        int contRetidosMax = 0;
        int indMax = 0;
        for(int i = 0; i < Siglas.length; i++){
            contRetidosAtual = 0;
            for(int ii = 0 ; ii < estud.getTamanho(); ii++){
                Estudante atual = estud.valorDe(ii);
                double[] nota = atual.getNotas();
                if(nota[i] < 5.0){
                    contRetidosAtual++;
                }
                if(contRetidosAtual > contRetidosMax){
                    contRetidosMax = contRetidosAtual;
                    indMax = i;
                }
            }
        }
        return Siglas[indMax];
    }

    public static Estudante estudMaiorMedia() throws Exception {
        double mediaMax = 0.0;
        int indMax = 0;
        for(int i = 0 ; i < estud.getTamanho(); i++){
           Estudante atual =  estud.valorDe(i);
           if(atual.mediaDasNotas() > mediaMax){
               mediaMax = atual.mediaDasNotas();
               indMax = i;
           }
        }
        return estud.valorDe(indMax);
    }

    public static double[] maioresEMenoresNotas(Estudante proc){
        double[] notas = new double[2];
        double menorNota = 10;
        double maiorNota = 0;
        double[] notasAluno = proc.getNotas();
        for(int i = 0 ; i < proc.getQuantasNotas(); i++){
            double notaAtual = notasAluno[i];
            if(notaAtual > maiorNota){
                maiorNota = notaAtual;
            }
            if(notaAtual < menorNota){
                menorNota = notaAtual;
            }
        }
        notas[0]= menorNota;
        notas[1] = maiorNota;
        return notas;
    }

    public static double mediaCurso(String curso){
        int indCurso = -1;
        for(int i = 0 ; i < Siglas.length;i++){
            if(Siglas[i].equals(curso)){
                indCurso = i;
                break;
            }
        }
        int contador = 0;
        double somaCurso = 0;
        for(int ii = 0 ; ii < estud.getTamanho();ii++){
            Estudante atual = estud.valorDe(ii);
            double[] notasAtual = atual.getNotas();
            somaCurso += notasAtual[indCurso];
            contador++;
        }
        return somaCurso/contador;
    }

    public static Estudante maiorNotaMenorMedia(){
        int indCurso = 0;
        double mediaMax = 10;
        double mediaAtual;
        for(int i =0 ; i < Siglas.length; i++){
            mediaAtual = mediaCurso(Siglas[i]);
            if(mediaAtual < mediaMax){
                mediaMax = mediaAtual;
                indCurso = i;
            }
        }
        double maiorNota = 0;
        int indEstudante = 0;
        for(int i = 0 ; i < estud.getTamanho();i++){
            Estudante atual = estud.valorDe(i);
            double[] notas = atual.getNotas();
            if(notas[indCurso] > maiorNota){
                maiorNota = notas[indCurso];
                indEstudante = i;
            }
        }

        return estud.valorDe(indEstudante);
    }

    public static Estudante menorNotaMaiorMedia(){
        int indCurso = 0;
        double mediaMax = 0;
        double mediaAtual;
        for(int i =0 ; i < Siglas.length; i++){
            mediaAtual = mediaCurso(Siglas[i]);
            if(mediaAtual > mediaMax){
                mediaMax = mediaAtual;
                indCurso = i;
            }
        }
        double maiorNota = 10;
        int indEstudante = 0;
        for(int i = 0 ; i < estud.getTamanho();i++){
            Estudante atual = estud.valorDe(i);
            double[] notas = atual.getNotas();
            if(notas[indCurso] < maiorNota){
                maiorNota = notas[indCurso];
                indEstudante = i;
            }
        }

        return estud.valorDe(indEstudante);
    }

}