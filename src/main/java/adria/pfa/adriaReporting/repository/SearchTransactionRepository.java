package adria.pfa.adriaReporting.repository;

import adria.pfa.adriaReporting.dto.TransactionDto;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchTransactionRepository {
    Page<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDto transactionDto, Pageable page);
}
