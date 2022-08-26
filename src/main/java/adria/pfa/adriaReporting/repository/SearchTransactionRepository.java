package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


//public interface SearchTransactionRepository {
//    List<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDao transactionDao);
//}

public interface SearchTransactionRepository {
    Page<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDao transactionDao, Pageable page);
}
