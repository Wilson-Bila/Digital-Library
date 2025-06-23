import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const LivrosList = ({ user }) => {
    const [livros, setLivros] = useState([]);
    const [compras, setCompras] = useState([]);
    const [error, setError] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }

        const fetchLivros = async () => {
            try {
                const response = await axios.get('http://localhost:8080/api/livros');
                setLivros(response.data);
            } catch (err) {
                console.error(err);
            }
        };

        const fetchCompras = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/compras/usuario/${user.id}`);
                setCompras(response.data);
            } catch (err) {
                console.error(err);
            }
        };

        fetchLivros();
        fetchCompras();
    }, [user, navigate]);

    const comprarLivro = async (livroId) => {
        try {
            await axios.post(`http://localhost:8080/api/compras/usuario/${user.id}/livro/${livroId}`);
            alert('Livro comprado com sucesso!');
            window.location.reload();
        } catch (err) {
            setError(err.response?.data?.message || 'Erro ao comprar livro');
        }
    };

    const jaComprado = (livroId) => {
        return compras.some(compra => compra.livro.id === livroId);
    };

    return (
        <div className="container mt-4">
            <h2>Livros Disponíveis</h2>
            <p>Saldo: R$ {user?.saldo?.toFixed(2)}</p>
            {error && <div className="alert alert-danger">{error}</div>}
            <div className="row">
                {livros.map(livro => (
                    <div key={livro.id} className="col-md-4 mb-4">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{livro.titulo}</h5>
                                <p className="card-text">{livro.autor}</p>
                                <p className="card-text">{livro.descricao}</p>
                                <p className="card-text">Preço: R$ {livro.preco.toFixed(2)}</p>
                                {jaComprado(livro.id) ? (
                                    <button className="btn btn-secondary" disabled>Já adquirido</button>
                                ) : (
                                    <button 
                                        className="btn btn-primary" 
                                        onClick={() => comprarLivro(livro.id)}
                                        disabled={user.saldo < livro.preco}
                                    >
                                        Comprar
                                    </button>
                                )}
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default LivrosList;