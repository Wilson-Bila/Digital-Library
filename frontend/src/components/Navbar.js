import React from 'react';
import { Link, useNavigate } from 'react-router-dom';

const Navbar = ({ user, setUser }) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        setUser(null);
        navigate('/login');
    };

    return (
        <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
            <div className="container">
                <Link className="navbar-brand" to="/">Biblioteca Digital</Link>
                <div className="collapse navbar-collapse">
                    <ul className="navbar-nav me-auto">
                        {user && (
                            <>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/livros">Livros</Link>
                                </li>
                                <li className="nav-item">
                                    <Link className="nav-link" to="/minhas-compras">Minhas Compras</Link>
                                </li>
                            </>
                        )}
                    </ul>
                    {user && (
                        <div className="d-flex">
                            <span className="navbar-text me-3">Olá, {user.nome}</span>
                            <button className="btn btn-outline-light" onClick={handleLogout}>Sair</button>
                        </div>
                    )}
                </div>
            </div>
        </nav>
    );
};

export default Navbar;