-- Insert Payment Methods
INSERT INTO payment_methods (name, code, description, logo_url, is_active, is_online, processing_fee_percentage, processing_fee_fixed, min_amount, max_amount, sort_order, created_at, updated_at) VALUES
('VNPay', 'VNPAY', 'Thanh toán qua VNPay - Hỗ trợ thẻ ATM, Internet Banking, QR Code', 'https://cdn.vnpay.vn/logo/vnpay-logo.png', true, true, 1.5, 0, 10000, 50000000, 1, NOW(), NOW()),
('MoMo', 'MOMO', 'Thanh toán qua ví điện tử MoMo', 'https://cdn.momo.vn/logo/momo-logo.png', true, true, 1.0, 0, 10000, 20000000, 2, NOW(), NOW()),
('ZaloPay', 'ZALOPAY', 'Thanh toán qua ví điện tử ZaloPay', 'https://cdn.zalopay.vn/logo/zalopay-logo.png', true, true, 1.0, 0, 10000, 20000000, 3, NOW(), NOW()),
('Chuyển khoản ngân hàng', 'BANK_TRANSFER', 'Chuyển khoản trực tiếp qua ngân hàng', 'https://cdn.shopcuathuy.com/logo/bank-transfer.png', true, false, 0, 0, 50000, 100000000, 4, NOW(), NOW()),
('Thanh toán khi nhận hàng', 'COD', 'Thanh toán bằng tiền mặt khi nhận hàng', 'https://cdn.shopcuathuy.com/logo/cod.png', true, false, 0, 0, 0, 5000000, 5, NOW(), NOW()),
('Thẻ tín dụng', 'CREDIT_CARD', 'Thanh toán bằng thẻ tín dụng quốc tế', 'https://cdn.shopcuathuy.com/logo/credit-card.png', true, true, 2.5, 0, 50000, 100000000, 6, NOW(), NOW()),
('Thẻ ghi nợ', 'DEBIT_CARD', 'Thanh toán bằng thẻ ghi nợ', 'https://cdn.shopcuathuy.com/logo/debit-card.png', true, true, 1.5, 0, 10000, 50000000, 7, NOW(), NOW());

-- Update config for VNPay
UPDATE payment_methods 
SET config_json = '{"tmnCode": "2QXUI4J4", "secretKey": "RAOEXHYVHDDIIENYWSLGJSPUKTWMEGQS", "url": "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html", "returnUrl": "http://localhost:3000/payment/return"}'
WHERE code = 'VNPAY';

-- Update config for MoMo
UPDATE payment_methods 
SET config_json = '{"partnerCode": "MOMO_PARTNER_CODE", "accessKey": "MOMO_ACCESS_KEY", "secretKey": "MOMO_SECRET_KEY", "endpoint": "https://test-payment.momo.vn/v2/gateway/api/create"}'
WHERE code = 'MOMO';

-- Update config for ZaloPay
UPDATE payment_methods 
SET config_json = '{"appId": "ZALOPAY_APP_ID", "key1": "ZALOPAY_KEY1", "key2": "ZALOPAY_KEY2", "endpoint": "https://sb-openapi.zalopay.vn/v2/create"}'
WHERE code = 'ZALOPAY';
