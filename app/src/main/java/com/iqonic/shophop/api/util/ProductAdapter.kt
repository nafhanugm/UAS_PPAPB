package com.iqonic.shophop.api.util

import com.iqonic.shophop.api.apiModel.entity.Product
import com.iqonic.shophop.api.apiModel.entity.Attribute
import com.iqonic.shophop.models.ProductModel
import com.iqonic.shophop.models.Attributes
import com.iqonic.shophop.models.Categories
import com.iqonic.shophop.models.Dimensions
import com.iqonic.shophop.models.ImagesData


class ProductAdapter {
    companion object{
        fun ProductToProductModel(product: Product): ProductModel {
            return ProductModel(
                attributes = product.attributes.map {
                    Attributes(
                        id = it.id.toDouble(),
                        name = it.name,
                        options = ArrayList(it.options),
                        position = it.position.toDouble(),
                        variation = it.variation,
                        visible = it.visible
                    )
                },
                average_rating = product.averageRating,
                backordered = product.backordered,
                backorders = product.backorders,
                backorders_allowed = product.backordersAllowed,
                button_text = product.buttonText,
                catalog_visibility = product.catalogVisibility,
                categories = product.categories.map {
                    Categories(
                        id = 0,
                        name = it.name,
                        slug = ""
                    )
                },
                cross_sell_ids = product.crossSellIds,
                date_created = product.dateCreated,
                date_created_gmt = product.dateCreatedGmt,
                date_modified = product.dateModified,
                date_modified_gmt = product.dateModifiedGmt,
                date_on_sale_from = product.dateOnSaleFrom as Any,
                date_on_sale_from_gmt = product.dateOnSaleFromGmt as Any,
                date_on_sale_to = product.dateOnSaleTo as Any,
                date_on_sale_to_gmt = product.dateOnSaleToGmt as Any,
                default_attributes = product.defaultAttributes,
                description = product.description,
                dimensions = Dimensions(
                    height = product.dimensions.height,
                    length = product.dimensions.length,
                    width = product.dimensions.width
                ),
                download_expiry = product.downloadExpiry,
                download_limit = product.downloadLimit,
                downloadable = product.downloadable,
                downloads = product.downloads,
                external_url = product.externalUrl,
                featured = product.featured,
                grouped_products = emptyList(),
                id = product.id,
                images = product.images.map {
                    ImagesData(
                        id = it.id.toString(),
                        src = it.src,
                        name = it.name
                    )
                },
                manage_stock = product.manageStock,
                menu_order = 0,
                meta_data = emptyList(),
                name = product.name,
                on_sale = product.onSale,
                parent_id = product.parentId,
                permalink = "",
                price = product.price,
                price_html = product.priceHtml,
                purchasable = product.purchasable,
                purchase_note = product.purchaseNote,
                rating_count = product.ratingCount,
                regular_price = product.regularPrice,
                related_ids = product.relatedIds.map { it.toString().toInt() },
                reviews_allowed = product.reviewsAllowed,
                sale_price = product.salePrice,
                shipping_class = product.shippingClass,
                shipping_class_id = product.shippingClassId,
                shipping_required = product.shippingRequired,
                shipping_taxable = product.shippingTaxable,
                short_description = product.shortDescription,
                sku = product.sku,
                slug = "",
                sold_individually = product.soldIndividually,
                status = product.status,
                stock_quantity = product.stockQuantity,
                stock_status = product.stockStatus,
                tags = product.tags,
                tax_class = product.taxClass,
                tax_status = product.taxStatus,
                total_sales = product.totalSales,
                type = product.type,
                upsell_ids = product.upsellIds,
                variations = emptyList(),
                virtual = product.virtual,
                weight = product.weight,
                image = product.images.firstOrNull()?.src ?: ""
            )
        }
    }
}