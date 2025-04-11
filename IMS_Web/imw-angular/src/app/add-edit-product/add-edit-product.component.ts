import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../service/api.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-add-edit-product',
  imports: [CommonModule, FormsModule],
  templateUrl: './add-edit-product.component.html',
  styleUrl: './add-edit-product.component.css',
})
export class AddEditProductComponent implements OnInit {
  productId: string | null = null;
  name: string = '';
  price: string = '';
  sku: string = '';
  stockQuantity: string = '';
  description: string = '';
  categoryId: string = '';
  imageFile: File | null = null;
  imageUrl: string = '';
  isEditing: boolean = false;
  categories: any[] = [];
  message: string = '';

  constructor(
    private apiService: ApiService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.productId = this.route.snapshot.paramMap.get('productId');
    this.fetchCategories();
    if (this.productId) {
      this.isEditing = true;
      this.fetchProductById(this.productId);
    }
  }

  //get All Categories
  fetchCategories(): void {
    this.apiService.getAllCategory().subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          this.categories = res.categories;
        }
      },
      error: (error) => {
        this.showMessage(
          error?.error?.message ||
            error?.message ||
            'Unable to Fetch categories'
        );
      },
    });
  }

  //get Product by id
  fetchProductById(productId: string): void {
    this.apiService.getProductById(productId).subscribe({
      next: (res: any) => {
        if (res.status === 200) {
          const product = res.product;

          this.name = product.name;
          this.sku = product.sku;
          this.price = product.price;
          this.stockQuantity = product.stockQuantity;
          this.categoryId = product.categoryId;
          this.description = product.description;
          this.imageUrl = product.imageUrl;
        } else {
          this.showMessage(res.message);
        }
      },
      error: (error) => {
        this.showMessage('Unable to get Product By Id');
      },
    });
  }

  handelImageChnage(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input?.files?.[0]) {
      this.imageFile = input.files[0];
      const reader = new FileReader();
      reader.onloadend = () => {
        this.imageUrl = reader.result as string;
      };
      reader.readAsDataURL(this.imageFile);
    }
  }

  handleSubmit(event: Event): void {
    event.preventDefault();
    const formData = new FormData();
console.log('this.categoryId '+this.categoryId)
    formData.append('name', this.name);
    formData.append('sku', this.sku);
    formData.append('price', this.price);
    formData.append('stockQuantity', this.stockQuantity);
    formData.append('description', this.description);
    formData.append('categoryId', this.categoryId);

    if (this.imageFile) {
      formData.append('imageFile', this.imageFile);
    }

    if (this.isEditing) {
      formData.append('id', this.productId!);
      this.apiService.updateProduct(formData).subscribe({
        next: (res: any) => {
          if (res.status === 200) {
            this.showMessage('Product Updated Successfully');
            this.router.navigate(['/product'])
          }
        },
        error: (error) => {
          this.showMessage(
            error?.error?.message ||
              error?.message ||
              'Unable to Update product'
          );
        },
      });
    }else{
      
      this.apiService.addProduct(formData).subscribe({
        next: (res: any) => {
          if (res.status === 200) {
            this.showMessage('Product Save Successfully');
            this.router.navigate(['/product'])
          }
        },
        error: (error) => {
          this.showMessage(
            error?.error?.message ||
              error?.message ||
              'Unable to Save product'
          );
        },
      });
    }
  }
  showMessage(message: string) {
    this.message = message;
    setTimeout(() => {
      this.message = '';
    }, 4000);
  }
}
