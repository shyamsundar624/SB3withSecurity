import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { PaginationComponent } from '../pagination/pagination.component';
import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transaction',
  imports: [CommonModule,FormsModule,PaginationComponent],
  templateUrl: './transaction.component.html',
  styleUrl: './transaction.component.css'
})
export class TransactionComponent implements OnInit{

  transactions:any[]=[];
  searchInput:string='';
  valueToSearch:string='';
  message: string = '';
  currentPage: number = 0;
  totalPage: number = 0;
  itemsPerPage = 10;

  constructor(
    private apiService:ApiService,
    private router:Router
  ){}
  ngOnInit(): void {
    this.loadTransactions();
  }
  loadTransactions():void {
    this.apiService.getAllTransactions(this.valueToSearch).subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          const transactions = res.transactions || [];
          
          this.totalPage = Math.ceil(transactions.length / this.itemsPerPage);
          this.transactions = transactions.slice(
            (this.currentPage - 1) * this.itemsPerPage,
            this.currentPage * this.itemsPerPage
          );
          this.transactions=transactions;
          console.log(this.transactions)
        }
      },
      error: (error) => {
        this.showMessage(
          error?.error?.message || error?.message || 'Unable to get All Transactions'
        );
      },
    });
  }

  // Handel Search

  handleSearch():void{
this.currentPage=1;
this.valueToSearch=this.searchInput;
this.loadTransactions();
  }

//navigate to Txn Details page

navigateToTransactionDetailsPage(transactionId:string):void{
  this.router.navigate([`/transaction/${transactionId}`])
}

//Handle Page navigation
onPageChange(page:number):void{
  this.currentPage=page;
  this.loadTransactions();
}
  showMessage(message:string){
    this.message=message;
    setTimeout(() => {
      this.message='';
    }, 4000);
  }
}
