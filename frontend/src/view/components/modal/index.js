import React from "react";
import {Modal, Button} from "antd";

const CustomModal = ({children, title, visible, onOk, onCancel, ...hooks}) => {
  const footerHandler = () =>
    onOk
      ? [
          <Button key="submit" type="primary" onClick={onOk}>
            Submit
          </Button>,
        ]
      : [];
  return (
    <Modal
      title={title}
      visible={visible}
      onCancel={onCancel}
      footer={[
        <Button key="cancel" onClick={onCancel}>
          Cancel
        </Button>,
        ...footerHandler(),
      ]}
    >
      {React.cloneElement(children, hooks)}
    </Modal>
  );
};

export default CustomModal;
