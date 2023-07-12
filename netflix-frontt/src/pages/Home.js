import React from 'react';
import Row from "../components/Row";
import Banner from "../components/Banner";
import Nav from "../components/Nav";
import { categories } from '../api';

function Home() {
    return (
        <>
            <Nav />
            <Banner />
            {categories.map((category) => (
                <Row
                    key={category.name}
                    title={category.title}
                    path={category.path}
                    isLarge={category.isLarge}
                />
            ))}
        </>
    );
}

export default Home;
