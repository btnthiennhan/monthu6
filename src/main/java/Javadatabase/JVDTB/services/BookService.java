/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Javadatabase.JVDTB.services;

import Javadatabase.JVDTB.entity.Book;
import Javadatabase.JVDTB.repository.IBookRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Admin
 */
@Service
public class BookService {
    @Autowired
    private IBookRepository bookRepository;
    
    public List<Book> getAllBook(){
        return bookRepository.findAll();
    }
    public Book getBookById(Long id){
        return  bookRepository.findById(id).orElse(null);
    }
    public void addBook(Book book){
        bookRepository.save(book);
    }
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }
    public void updateBook(Book book){
        bookRepository.save(book);
    }

}
