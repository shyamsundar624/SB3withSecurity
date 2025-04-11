import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../service/api.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-transaction-details',
  imports: [CommonModule, FormsModule],
  templateUrl: './transaction-details.component.html',
  styleUrl: './transaction-details.component.css',
})
export class TransactionDetailsComponent implements OnInit {
  transactionId: string = '';
  transaction: any = null;
  status: string = '';
  message: string = '';

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  ngOnInit(): void {
    //  extract transactionId from route
    this.route.params.subscribe((params) => {
      this.transactionId = params['transactionId'];
      this.getTransactionDetails();
    });
  }
  getTransactionDetails(): void {
    if (this.transactionId) {
      this.apiService.getTransactionById(this.transactionId).subscribe({
        next: (res: any) => {
          if (res.status === 200) {
            this.transaction = res.transaction;
            this.status = this.transaction.status;
          }
        },
        error: (error) => {
          this.showMessage(
            error?.error?.message ||
              error?.message ||
              'Unable to get Transaction' + error
          );
        },
      });
    }
  }

  //Update Status
 handleUpdateStatus():void{
  this.apiService.updateTransactionStatus(this.transactionId,this.status).subscribe({
    next: (res: any) => {
      if (res.status === 200) {
       this.router.navigate([`/transaction`])
      }
    },
    error: (error) => {
      this.showMessage(
        error?.error?.message ||
          error?.message ||
          'Unable to Update Transaction' + error
      );
    },
  })
 }

  showMessage(message: string) {
    this.message = message;
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}
