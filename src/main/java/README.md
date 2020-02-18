# Desafio Tatic
Teste prático Tatic.

# Solução
Para armazenamento foi usado como indice, parte da data do evento(ano,mês,dia), e para cada dia armazenado um arquivo compactado em blocos, o nome do arquivo sendo seu indice, cada bloco possui um header indicando a data maxima e minima, evento maximo e minmo. 

Para cada arquivo compactado é armazenado um arquivo de headers para cada arquivo compactado, contendo um lista de posições dos headers dos blocos compactados.

Após esse processamento do arquivo e armazenamento compactado, sua busca e facilitada.

Primeiro passo da busca é a procura dos arquivos pelo seu nome e que fazem parte do invertalo de datas solicitado pelo usuário. O próximo passo é feita a busca dos headers do arquivos onde estarão as posiçoes dos headers dos blocos compactados.

Sendo encontrados os arquivos (compactado e headers) e suas posiçoes de header é feita uma busca binaria para se encontrar os headers que indicam em quais blocos compactados está a informação.

Ultimo passo é feita a descompactação dos blocos compactados onde contém a informação.

# Compilação e execução

Primeiro passo é abrir o arquivo Util.java e inserir na variavel PATH o caminho completo do diretorio onde estará o arquivo texto a ser armazenado.
Após, abra seu terminal no diretorio onde está o codigo fonte.

# Compilação 
javac Armazenador.java

javac Buscador.java

# Execução
Para execução do armazenador deve se colocar o nome do arquivo a ser processado, ex:

java Armazenador.java sample.txt

Para execução do buscador deve se colocar o intervalo de datas e os indices de eventos desejados, ex:

java Buscador.java 20170114103133210 20170114103133210 B567F8B8

