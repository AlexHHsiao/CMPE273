import React, {useState} from "react";
import Carousel, {Modal, ModalGateway} from "react-images";
import Gallery from "react-photo-gallery";
import {imageGridHandler} from "../../utils/helpers/home";

const HomeGallery = ({imageData}) => {
  const [currentImage, setCurrentImage] = useState(0);
  const [viewerIsOpen, setViewerIsOpen] = useState(false);

  const openLightbox = (event, {index}) => {
    setCurrentImage(index);
    setViewerIsOpen(true);
  };

  const closeLightbox = () => {
    setCurrentImage(0);
    setViewerIsOpen(false);
  };

  return (
    <>
      <Gallery photos={imageGridHandler(imageData)} onClick={openLightbox} />
      <ModalGateway>
        {viewerIsOpen ? (
          <Modal onClose={closeLightbox}>
            <Carousel
              currentIndex={currentImage}
              views={imageGridHandler(imageData).map((x) => ({
                ...x,
              }))}
            />
          </Modal>
        ) : null}
      </ModalGateway>
    </>
  );
};

export default HomeGallery;
