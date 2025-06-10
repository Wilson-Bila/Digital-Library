document.addEventListener('DOMContentLoaded', function() {
    const livroForm = document.getElementById('livroForm');
    const mensagemDiv = document.getElementById('mensagem');

    livroForm.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const preco = parseFloat(document.getElementById('preco').value);
        if (isNaN(preco)) {
            mostrarMensagem('Por favor, insira um preço válido', 'erro');
            return;
        }
        
        const livro = {
            titulo: document.getElementById('titulo').value.trim(),
            autor: document.getElementById('autor').value.trim(),
            descricao: document.getElementById('descricao').value.trim(),
            categoria: document.getElementById('categoria').value.trim(),
            preco: preco
        };
        
        enviarLivroParaBackend(livro);
    });

    async function enviarLivroParaBackend(livro) {
        mostrarMensagem('Enviando dados do livro...', 'info');
        
        try {
            const url = 'http://localhost:8080/api/livros';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(livro)
            });
            
            const data = await tratarResposta(response);
            
            if (data) {
                mostrarMensagem('Livro cadastrado com sucesso!', 'sucesso');
                livroForm.reset();
                setTimeout(() => {
                    window.location.href = 'Listar_Livros.html'; 
                }, 2000);
            }
        } catch (error) {
            tratarErro(error);
        }
    }

    async function tratarResposta(response) {
        if (!response.ok) {
            let errorMessage = 'Erro ao cadastrar livro';
            
            try {
                const errorData = await response.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                } else if (typeof errorData === 'string') {
                    errorMessage = errorData;
                }
            } catch (e) {
                const text = await response.text();
                if (text) errorMessage = text;
            }
            
            throw new Error(errorMessage);
        }
        
        return response.json();
    }

    function mostrarMensagem(texto, tipo = 'info') {
        mensagemDiv.innerHTML = `<p class="${tipo}">${texto}</p>`;
        
        mensagemDiv.scrollIntoView({ behavior: 'smooth' });
    }

    function tratarErro(error) {
        console.error('Erro:', error);
        
        let mensagemErro = error.message;
        
        if (error.message.includes('Failed to fetch')) {
            mensagemErro = 'Não foi possível conectar ao servidor. Verifique:';
            mensagemErro += '<br>1. Se o backend está rodando';
            mensagemErro += '<br>2. Console do navegador (F12 > Rede)';
        }
        
        mostrarMensagem(mensagemErro, 'erro');
    }
});