# Documentação da API

## Instale as Dependências
```bash
lein deps
```

## Execute o Servidor
```bash
lein run
```

---

## 📚 Endpoints

### 1. Autenticação de Usuário
- **Rota**: `/login`
- **Método**: `POST`
- **Descrição**: Autentica um usuário com base no e-mail e senha.

**Exemplo de Requisição:**
```json
{
  "email": "admin@example.com",
  "senha": "123"
}
```

**Exemplo de Resposta:**
```json
{
  "status": 200,
  "message": "Usuário autenticado com sucesso",
  "data": {
    "id": 1,
    "nome": "Administrador",
    "email": "admin@example.com",
    "isadmin": true
  }
}
```

---

### 2. Salvar Notícias
- **Rota**: `/news/save`
- **Método**: `POST`
- **Descrição**: Busca notícias de uma API externa e salva no banco de dados.

**Exemplo de Resposta:**
```json
{
  "status": 200,
  "message": "Notícias salvas com sucesso!"
}
```

---

### 3. Listar Usuários
- **Rota**: `/getAllUser`
- **Método**: `GET`
- **Descrição**: Retorna todos os usuários cadastrados no banco de dados.

**Exemplo de Resposta:**
```json
{
  "status": 200,
  "data": [
    {
      "id": 1,
      "nome": "Administrador",
      "email": "admin@example.com",
      "isadmin": true
    },
    {
      "id": 2,
      "nome": "Usuário Normal",
      "email": "user@example.com",
      "isadmin": false
    }
  ]
}
```

---

## 🗊 Estrutura do Banco de Dados

### Tabela `usuarios`
| Campo    | Tipo     | Descrição                 |
|----------|----------|-----------------------------|
| `id`     | SERIAL   | Identificador único        |
| `nome`   | TEXT     | Nome do usuário            |
| `email`  | TEXT     | E-mail único do usuário   |
| `senha`  | TEXT     | Senha do usuário           |
| `isadmin`| BOOLEAN  | Se o usuário é admin       |

### Tabela `noticias`
| Campo            | Tipo       | Descrição                      |
|------------------|------------|----------------------------------|
| `id`             | SERIAL     | Identificador único           |
| `title`          | TEXT       | Título da notícia             |
| `abstract`       | TEXT       | Resumo da notícia             |
| `url`            | TEXT       | URL única da notícia         |
| `published_date` | TIMESTAMP  | Data de publicação           |
| `source`         | TEXT       | Fonte da notícia              |
| `likes`          | INTEGER    | Número de curtidas            |
| `created_at`     | TIMESTAMP  | Data de criação do registro   |

---

## 🛠️ Desenvolvimento

### Principais Arquivos
- **`core.clj`**: Arquivo principal que define as rotas e inicializa o servidor.
- **`controllers.clj`**: Contém a lógica de negócios, como salvar notícias e autenticar usuários.
- **`infraConfigs.clj`**: Configurações de acesso às APIs externas e banco de dados.

### Testar Localmente
Use ferramentas como Postman ou cURL para testar os endpoints:

```bash
curl -X POST http://localhost:3000/login \
-H "Content-Type: application/json" \
-d '{"email": "admin@example.com", "senha": "123"}'
```

---

## 🤝 Contribuição
1. Faça um fork do repositório.
2. Crie uma branch para suas alterações:
   ```bash
   git checkout -b minha-feature
   ```
3. Envie suas alterações:
   ```bash
   git commit -m "Minha nova feature"
   git push origin minha-feature
   ```
4. Abra um Pull Request.

---

## 🖋️ Licença
Este projeto está sob a licença MIT. Consulte o arquivo LICENSE para mais detalhes.

---


