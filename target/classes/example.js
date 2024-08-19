
const characters = [
    {
        name: "Monkey D. Luffy",
        philosophy: "Existencialismo",
        description: "Luffy vive su vida con plena libertad, siguiendo sus propios deseos y valores. Representa la idea existencialista de que el individuo crea su propio significado y propósito."
    },
    {
        name: "Roronoa Zoro",
        philosophy: "Estoicismo",
        description: "Zoro muestra disciplina, autocontrol y determinación, elementos clave del estoicismo. Enfrenta la adversidad con calma y se enfoca en lo que puede controlar."
    },
    {
        name: "Nico Robin",
        philosophy: "Empirismo",
        description: "Robin busca la verdad a través de la observación y el estudio de la historia. Cree en aprender del pasado para comprender el presente, reflejando el pensamiento empirista."
    },
    {
        name: "Joy Boy",
        philosophy: "Idealismo",
        description: "Joy Boy representa un ideal o una promesa por cumplir. La conexión con la filosofía del idealismo se encuentra en la búsqueda de un futuro mejor basado en los sueños y la promesa de libertad."
    }
];

// Función para mostrar la relación entre personajes y filosofía
function showPhilosophy(characterName) {
    const character = characters.find(c => c.name.toLowerCase() === characterName.toLowerCase());
    
    if (character) {
        console.log(`Personaje: ${character.name}`);
        console.log(`Filosofía: ${character.philosophy}`);
        console.log(`Descripción: ${character.description}`);
    } else {
        console.log("Personaje no encontrado.");
    }
}

// Ejemplo de uso: Mostrar la filosofía relacionada con Luffy
showPhilosophy("Monkey D. Luffy");

// Ejemplo de uso: Mostrar la filosofía relacionada con Joy Boy
showPhilosophy("Joy Boy");

// Función para mostrar todas las relaciones de filosofía y personajes
function showAllPhilosophies() {
    characters.forEach(character => {
        console.log(`Personaje: ${character.name}`);
        console.log(`Filosofía: ${character.philosophy}`);
        console.log(`Descripción: ${character.description}`);
        console.log("---------------");
    });
}

// Mostrar todas las relaciones
showAllPhilosophies();
