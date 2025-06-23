import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const MinhasCompras = ({ user }) => {
    const [compras, setCompras] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        if (!user) {
            navigate('/login');
            return;
        }

        const fetchCompras = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/compras/usuario/${user.id}`);
                setCompras(response.data);
            } catch (err) {
                console.error(err);
            }
        };

        fetchCompras();
    }, [user, navigate]);

    return (
        <div className="container mt-4">
            <h2>Minhas Compras</h2>
            <div className="row">
                {compras.map(compra => (
                    <div key={compra.id} className="col-md-4 mb-4">
                        <div className="card">
                            <div className="card-body">
                                <h5 className="card-title">{compra.livro.titulo}</h5>
                                <p className="card-text">Autor: {compra.livro.autor}</p>
                                <p className="card-text">Comprado em: {new Date(compra.dataCompra).toLocaleString()}</p>
                                <p className="card-text">Preço: R$ {compra.livro.preco.toFixed(2)}</p>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default MinhasCompras;