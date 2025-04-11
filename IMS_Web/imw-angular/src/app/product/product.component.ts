import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';

import { ApiService } from '../service/api.service';
import { Router } from '@angular/router';
import { PaginationComponent } from '../pagination/pagination.component';

@Component({
  selector: 'app-product',
  imports: [CommonModule, PaginationComponent],
  templateUrl: './product.component.html',
  styleUrl: './product.component.css',
})
export class ProductComponent implements OnInit {
  products: any[] = [];
  message: string = '';
  currentPage: number = 0;
  totalPage: number = 0;
  itemsPerPage = 10;

  constructor(private apiService: ApiService, private router: Router) {}
  ngOnInit(): void {
    this.fetchProducts();
  }

  fetchProducts() {
    this.apiService.getAllProducts().subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          const products = res.products || [];
          
          this.totalPage = Math.ceil(products.length / this.itemsPerPage);
          this.products = products.slice(
            (this.currentPage - 1) * this.itemsPerPage,
            this.currentPage * this.itemsPerPage
          );
          this.products=products;
          console.log(this.products)
        }
      },
      error: (error) => {
        this.showMessage(
          error?.error?.message || error?.message || 'Unable to fetch Product'
        );
      },
    });
  }

  //Delete Product

  handleProductDelete(productId: string): void {
    if (window.confirm('Are you sure you want to delete this Produc')) {
      this.apiService.deleteProduct(productId).subscribe({
        next: (res: any) => {
          if (res.status === 200) {
            this.showMessage('Product Deleted Successfully');
            this.fetchProducts();
          }
        },
        error: (error) => {
          this.showMessage(
            error?.error?.message ||
              error?.message ||
              'Unable to Delete Product ' + error
          );
        },
      });
    }
  }

  //Nagiate to add Product
  navigateToAddProduct(): void {
    this.router.navigate(['/add-product']);
  }

  //Nagiate to add Product
  navigateToEditProduct(productId: string): void {
    this.router.navigate([`/edit-product/${productId}`]);
  }

  onPageChange(page: number): void {
    this.currentPage = page;
    this.fetchProducts();
  }
  showMessage(message: string) {
    this.message = message;

    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}
