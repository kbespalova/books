document.getElementById('bookForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const title = document.getElementById('title').value;
    const author = document.getElementById('author').value;
    const price = document.getElementById('price').value;

    fetch('/books', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ title, author, price })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to add book');
            }
            return response.json();
        })
        .then(data => {
            console.log('Book added:', data);
            document.getElementById('bookForm').reset();
            fetchBooks();
        })
        .catch(error => {
            console.error('Error:', error);
        });
});

function fetchBooks() {
    fetch('/books')
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to fetch books');
            }
            return response.json();
        })
        .then(data => {
            console.log('Books:', data);
            displayBooks(data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function displayBooks(books) {
    const bookList = document.getElementById('bookList');
    bookList.innerHTML = '';

    books.forEach(book => {
        const bookItem = document.createElement('div');
        bookItem.innerHTML = `<strong>Title:</strong> ${book.title}, <strong>Author:</strong> ${book.author}, <strong>Price:</strong> ${book.price}`;
        bookList.appendChild(bookItem);
    });
}

window.addEventListener('load', fetchBooks);

fetch('/api/books')
    .then(response => response.json())
    .then(data => {
        console.log('Отримано дані з бекенду:', data);
    })
    .catch(error => {
        console.error('Помилка отримання даних з бекенду:', error);
    });

const newBook = {
    title: 'Нова книга',
    author: 'Новий автор',
    price: 'Нова ціна',
};

fetch('/api/books', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(newBook),
})
    .then(response => response.json())
    .then(data => {
        console.log('Книга додана:', data);
    })
    .catch(error => {
        console.error('Помилка додавання книги:', error);
    });
