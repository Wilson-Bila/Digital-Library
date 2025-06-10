document.addEventListener('DOMContentLoaded', function() {
    const usuarioForm = document.getElementById('usuarioForm');
    const mensagemDiv = document.getElementById('mensagem');

    // Validação do formulário antes do envio
    usuarioForm.addEventListener('submit', function(event) {
        event.preventDefault();
        
        // Validação básica do frontend
        const senha = document.getElementById('senha').value;
        if (senha.length < 6) {
            mostrarMensagem('A senha deve ter pelo menos 6 caracteres', 'erro');
            return;
        }
        
        const usuario = {
            nomeCompleto: document.getElementById('nomeCompleto').value.trim(),
            email: document.getElementById('email').value.trim().toLowerCase(),
            senha: senha
        };
        
        enviarUsuarioParaBackend(usuario);
    });

    async function enviarUsuarioParaBackend(usuario) {
        // Mostrar loading
        mostrarMensagem('Enviando dados...', 'info');
        
        try {
            const url = 'http://localhost:8080/api/usuarios';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(usuario)
            });
            
            const data = await tratarResposta(response);
            
            if (data) {
                mostrarMensagem('Usuário cadastrado com sucesso!', 'sucesso');
                usuarioForm.reset();
                
                // Redirecionar ou fazer outra ação após cadastro
                setTimeout(() => {
                    window.location.href = 'Listar_Usuario.html'; // Se tiver uma página de login
                }, 2000);
            }
        } catch (error) {
            tratarErro(error);
        }
    }

    async function tratarResposta(response) {
        if (!response.ok) {
            let errorMessage = 'Erro ao cadastrar usuário';
            
            try {
                const errorData = await response.json();
                if (errorData.message) {
                    errorMessage = errorData.message;
                } else if (typeof errorData === 'string') {
                    errorMessage = errorData;
                }
            } catch (e) {
                // Se não conseguir parsear o JSON de erro
                const text = await response.text();
                if (text) errorMessage = text;
            }
            
            throw new Error(errorMessage);
        }
        
        return response.json();
    }

    function mostrarMensagem(texto, tipo = 'info') {
        mensagemDiv.textContent = texto;
        mensagemDiv.className = 'mensagem ' + tipo;
        
        // Rolagem suave para a mensagem
        mensagemDiv.scrollIntoView({ behavior: 'smooth' });
    }

    function tratarErro(error) {
        console.error('Erro:', error);
        
        // Mensagens mais amigáveis para erros comuns
        let mensagemErro = error.message;
        
        if (error.message.includes('Email já está em uso')) {
            mensagemErro = 'Este email já está cadastrado. Por favor, use outro email.';
        } else if (error.message.includes('Failed to fetch')) {
            mensagemErro = 'Não foi possível conectar ao servidor. Verifique sua conexão.';
        }
        
        mostrarMensagem(mensagemErro, 'erro');
        
        // Focar no campo com problema
        if (error.message.includes('email')) {
            document.getElementById('email').focus();
        }
    }
});