import React, { useState } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import LivrosList from './components/LivrosList';
import MinhasCompras from './components/MinhasCompras';
import Navbar from './components/Navbar';

function App() {
    const [user, setUser] = useState(null);

    return (
        <Router>
            <Navbar user={user} setUser={setUser} />
            <Routes>
                <Route path="/" element={<Navigate to="/livros" />} />
                <Route path="/login" element={user ? <Navigate to="/livros" /> : <Login setUser={setUser} />} />
                <Route path="/livros" element={user ? <LivrosList user={user} /> : <Navigate to="/login" />} />
                <Route path="/minhas-compras" element={user ? <MinhasCompras user={user} /> : <Navigate to="/login" />} />
            </Routes>
        </Router>
    );
}

export default App;