package com.iqonic.shophop.api.util

import android.util.Log
import com.iqonic.shophop.api.apiModel.entity.Product
import com.iqonic.shophop.api.apiModel.entity.Attribute
import com.iqonic.shophop.api.apiModel.entity.Category
import com.iqonic.shophop.models.ProductModel
import com.iqonic.shophop.models.Attributes
import com.iqonic.shophop.models.Categories
import com.iqonic.shophop.models.Dimensions
import com.iqonic.shophop.models.ImagesData
import org.xml.sax.helpers.ParserAdapter

class ProductAdapter {
    companion object {
        fun ProductToProductModel(product: Product): ProductModel {
            Log.d("ProductAdapter", "Product: $product")
            return ProductModel(
                attributes = product.attributes?.map {
                    Attributes(
                        id = it.id?.toDouble() ?: 0.0,
                        name = it.name ?: "",
                        options = ArrayList(it.options),
                        position = it.position?.toDouble() ?: 0.0,
                        variation = it.variation!!,
                        visible = it.visible!!
                    )
                } ?: emptyList(),
                average_rating = product.averageRating ?: "",
                backordered = product.backordered == true,
                backorders = product.backorders ?: "",
                backorders_allowed = product.backordersAllowed == true,
                button_text = product.buttonText ?: "",
                catalog_visibility = product.catalogVisibility ?: "",
                categories = product.categories?.map {
                    Categories(
                        id = 0,
                        name = it.name ?: "",
                        slug = ""
                    )
                } ?: emptyList(),
                cross_sell_ids = product.crossSellIds ?: emptyList(),
                date_created = product.dateCreated ?: "",
                date_created_gmt = product.dateCreatedGmt ?: "",
                date_modified = product.dateModified ?: "",
                date_modified_gmt = product.dateModifiedGmt ?: "",
                date_on_sale_from = product.dateOnSaleFrom ?: "",
                date_on_sale_from_gmt = product.dateOnSaleFromGmt ?: "",
                date_on_sale_to = product.dateOnSaleTo ?: "",
                date_on_sale_to_gmt = product.dateOnSaleToGmt ?: "",
                default_attributes = product.defaultAttributes ?: emptyList(),
                description = product.description ?: "",
                dimensions = if (product.dimensions != null) {
                    Dimensions(
                        height = product.dimensions.height ?: "",
                        length = product.dimensions.length ?: "",
                        width = product.dimensions.width ?: ""
                    )
                } else {
                    Dimensions("", "", "")
                },
                download_expiry = product.downloadExpiry ?: 0,
                download_limit = product.downloadLimit ?: 0,
                downloadable = product.downloadable == true,
                downloads = product.downloads ?: emptyList(),
                external_url = product.externalUrl ?: "",
                featured = product.featured == true,
                grouped_products = emptyList(),
                id = product.id ?: "",
                images = product.images?.map {
                    ImagesData(
                        id = it.id.toString(),
                        src = it.src ?: "",
                        name = it.name ?: ""
                    )
                } ?: emptyList(),
                manage_stock = product.manageStock == true,
                menu_order = 0,
                meta_data = emptyList(),
                name = product.name ?: "",
                on_sale = product.onSale == true,
                parent_id = product.parentId ?: 0,
                permalink = "",
                price = product.price ?: "",
                price_html = product.priceHtml ?: "",
                purchasable = product.purchasable == true,
                purchase_note = product.purchaseNote ?: "",
                rating_count = product.ratingCount ?: 0,
                regular_price = product.regularPrice ?: "",
                related_ids = product.relatedIds?.map { it.toString().toInt() } ?: emptyList(),
                reviews_allowed = product.reviewsAllowed == true,
                sale_price = product.salePrice ?: "",
                shipping_class = product.shippingClass ?: "",
                shipping_class_id = product.shippingClassId ?: 0,
                shipping_required = product.shippingRequired == true,
                shipping_taxable = product.shippingTaxable == true,
                short_description = product.shortDescription ?: "",
                sku = product.sku ?: "",
                slug = "",
                sold_individually = product.soldIndividually == true,
                status = product.status ?: "",
                stock_quantity = product.stockQuantity ?: 0,
                stock_status = product.stockStatus ?: "",
                tags = product.tags ?: emptyList(),
                tax_class = product.taxClass ?: "",
                tax_status = product.taxStatus ?: "",
                total_sales = product.totalSales ?: 0,
                type = product.type ?: "",
                upsell_ids = product.upsellIds ?: emptyList(),
                variations = emptyList(),
                virtual = product.virtual == true,
                weight = product.weight ?: "",
                image = product.images?.firstOrNull()?.src ?: ""
            )
        }

        fun ProductModelToProduct(product: ProductModel): Product {
            return (Product(
                id = product.id,
                name = product.name,
                dateCreated = product.date_created,
                dateCreatedGmt = product.date_created_gmt,
                dateModified = product.date_modified,
                dateModifiedGmt = product.date_modified_gmt,
                type = product.type,
                status = product.status,
                featured = product.featured,
                catalogVisibility = product.catalog_visibility,
                description = product.description,
                shortDescription = product.short_description,
                sku = product.sku,
                price = product.price,
                regularPrice = product.regular_price,
                salePrice = product.sale_price,
                dateOnSaleFrom = product.date_on_sale_from,
                dateOnSaleFromGmt = product.date_on_sale_from_gmt.toString(),
                dateOnSaleTo = product.date_on_sale_to.toString(),
                dateOnSaleToGmt = product.date_on_sale_to_gmt.toString(),
                priceHtml = product.price_html,
                onSale = product.on_sale,
                purchasable = product.purchasable,
                totalSales = product.total_sales,
                virtual = product.virtual,
                downloadable = product.downloadable,
                downloads = product.downloads,
                downloadLimit = product.download_limit,
                downloadExpiry = product.download_expiry,
                externalUrl = product.external_url,
                buttonText = product.button_text,
                taxStatus = product.tax_status,
                taxClass = product.tax_class,
                manageStock = product.manage_stock,
                stockQuantity = product.stock_quantity.toString().toIntOrNull(),
                stockStatus = product.stock_status,
                backorders = product.backorders,
                backordersAllowed = product.backorders_allowed,
                backordered = product.backordered,
                soldIndividually = product.sold_individually,
                weight = product.weight,
                dimensions = com.iqonic.shophop.api.apiModel.entity.Dimensions(
                    width = product.dimensions.width,
                    height = product.dimensions.height,
                    length = product.dimensions.length
                ),
                shippingClass = product.shipping_class,
                shippingClassId = product.shipping_class_id,
                shippingRequired = product.shipping_required,
                shippingTaxable = product.shipping_taxable,
                reviewsAllowed = product.reviews_allowed,
                averageRating = product.average_rating,
                ratingCount = product.rating_count,
                relatedIds = product.related_ids.map { it.toString().toInt() },
                upsellIds = product.upsell_ids,
                crossSellIds = product.cross_sell_ids,
                parentId = product.parent_id,
                purchaseNote = product.purchase_note,
                categories = product.categories.map {
                   Category(
                        name = it.name,
                   )
                },
                tags = product.tags,
                attributes = product.attributes.map {
                    Attribute(
                        id = it.id.toInt(),
                        name = it.name,
                        position = it.position.toInt(),
                        visible = it.visible,
                        variation = it.variation,
                        options = it.options
                    )
                },
                images = product.images.map {
                    com.iqonic.shophop.api.apiModel.entity.Image(
                        id = it.id.toInt(),
                        src = it.src,
                        name = it.name,
                        dateCreated = null,
                        dateModified = null,
                        alt = null,
                        dateCreatedGmt = null,
                        dateModifiedGmt = null
                    )
                },
                defaultAttributes = product.default_attributes,

            ))
        }
    }
}