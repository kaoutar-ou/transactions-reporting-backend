package adria.pfa.adriaReporting.repository.Impl;

import adria.pfa.adriaReporting.dto.TransactionDto;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.SearchTransactionRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchTransactionRepositoryImpl implements SearchTransactionRepository {

    private EntityManager entityManager;

    private TransactionRepository transactionRepository;

    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Page<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDto transaction, Pageable page) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> root = criteriaQuery.from(Transaction.class);

        if (transaction != null) {

            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("client"), client));

            if (transaction.getReference() != null && !transaction.getReference().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("reference"), "%"+transaction.getReference()+"%"));
            }
            if (transaction.getTypeTransaction() != null) {
                predicates.add(criteriaBuilder.equal(root.get("typeTransaction"), transaction.getTypeTransaction()));
            }
            if (transaction.getTypeProduit() != null) {
                predicates.add(criteriaBuilder.equal(root.get("typeProduit"), transaction.getTypeProduit()));
            }
            if (transaction.getBeneficiaire_id() != null) {
                predicates.add(criteriaBuilder.equal(root.get("beneficiaire").get("id"), transaction.getBeneficiaire_id()));
            }
            if (transaction.getDateCreation() != null) {
                predicates.add(criteriaBuilder.like(root.get("dateCreation").as(String.class), transaction.getDateCreation().toString().substring(0,10) + "%"));
            }
            if (transaction.getDateExpiration() != null) {
                predicates.add(criteriaBuilder.like(root.get("dateExpiration").as(String.class), transaction.getDateExpiration().toString().substring(0,10) + "%"));
            }

            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Transaction> typedQuery = entityManager.createQuery(criteriaQuery);

            List<Transaction> transactionList = typedQuery.getResultList();

            int start = (int) page.getOffset();
            int end = Math.min((start + page.getPageSize()), transactionList.size());
            if(start > transactionList.size())
                return new PageImpl<>(new ArrayList<>(), page, transactionList.size());
            return new PageImpl<>(transactionList.subList(start, end), page, transactionList.size());
        }
        return transactionRepository.findByClient(client, page);
    }
}
