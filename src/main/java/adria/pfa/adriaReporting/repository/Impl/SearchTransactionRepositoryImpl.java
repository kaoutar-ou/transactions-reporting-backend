package adria.pfa.adriaReporting.repository.Impl;

import adria.pfa.adriaReporting.dao.TransactionDao;
import adria.pfa.adriaReporting.model.Client;
import adria.pfa.adriaReporting.model.Transaction;
import adria.pfa.adriaReporting.repository.SearchTransactionRepository;
import adria.pfa.adriaReporting.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SearchTransactionRepositoryImpl implements SearchTransactionRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionRepository transactionRepository;

//    @Override
//    public List<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDao transaction) {
//
//        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
//        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
//        Root<Transaction> root = criteriaQuery.from(Transaction.class);
//
//        System.out.println("transaction");
//        System.out.println(transaction);
//        if (transaction != null) {
//            List<Predicate> predicates = new ArrayList<Predicate>();
//            predicates.add(criteriaBuilder.equal(root.get("client"), client));
//
//            if (transaction.getReference() != null && !transaction.getReference().isEmpty()) {
//                predicates.add(criteriaBuilder.like(root.get("reference"), "%"+transaction.getReference()+"%"));
//            }
//            if (transaction.getTypeTransaction() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("typeTransaction"), transaction.getTypeTransaction()));
//            }
//            if (transaction.getTypeProduit() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("typeProduit"), transaction.getTypeProduit()));
//            }
//            if (transaction.getBeneficiaire_id() != null) {
//                predicates.add(criteriaBuilder.equal(root.get("beneficiaire").get("id"), transaction.getBeneficiaire_id()));
//            }
//            if (transaction.getDateCreation() != null) {
//                predicates.add(criteriaBuilder.like(root.get("dateCreation").as(String.class), transaction.getDateCreation().toString().substring(0,10) + "%"));
//            }
//
//            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
//            return entityManager.createQuery(criteriaQuery).getResultList();
//        }
//        return transactionRepository.findAllByClient(client);
//    }

    @Override
    public Page<Transaction> searchTransactionsByClientAndCriteria(Client client, TransactionDao transaction, Pageable page) {

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
//            System.out.println("transaction.dateCreation().toString().substring(0,10)");
//            System.out.println(transaction.getDateCreation().toString().substring(0,10));
//            System.out.println(root.get("dateCreation").as(String.class));
//
//            System.out.println("transaction.getDateExpiration().toString().substring(0,10)");
//            System.out.println(transaction.getDateExpiration().toString().substring(0,10));
//            System.out.println(root.get("dateExpiration").as(String.class));
            if (transaction.getDateExpiration() != null) {
                predicates.add(criteriaBuilder.like(root.get("dateExpiration").as(String.class), transaction.getDateExpiration().toString().substring(0,10) + "%"));
            }

//            Predicate[] predicatesArray = new Predicate[predicates.size()];
//            criteriaQuery.where(predicatesArray);
            criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));

            TypedQuery<Transaction> typedQuery = entityManager.createQuery(criteriaQuery);

            List<Transaction> transactionList = typedQuery.getResultList();

            int start = (int) page.getOffset();
            int end = Math.min((start + page.getPageSize()), transactionList.size());
            if(start > transactionList.size())
                return new PageImpl<>(new ArrayList<>(), page, transactionList.size());
            return new PageImpl<>(transactionList.subList(start, end), page, transactionList.size());

//            int size = typedQuery.getResultList().size();
//
//            Page<Transaction> transactionPage = new PageImpl<Transaction>(typedQuery.getResultList(), page, size);
//
//            System.out.println("typedQuery.getResultList()");
////            System.out.println(transactionPage.getContent());
//            return transactionPage;
        }
        System.out.println("transactionRepository.findByClient(client, page))");
//        System.out.println(typedQuery.getResultList());
        return transactionRepository.findByClient(client, page);
    }
}
